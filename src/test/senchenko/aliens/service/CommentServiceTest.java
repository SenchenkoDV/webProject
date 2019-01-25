package test.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.FrontServlet;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.dao.CommentDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.entity.Race;
import com.senchenko.aliens.entity.Role;
import com.senchenko.aliens.entity.User;
import com.senchenko.aliens.service.CommentService;
import com.senchenko.aliens.service.MonsterService;
import com.senchenko.aliens.service.UserRatingAction;
import com.senchenko.aliens.validation.CommentValidator;
import com.senchenko.aliens.validation.UserValidation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TransactionExecutor.class, RequestContent.class, SingletonDaoProvider.class, CommentDao.class, CommentValidator.class, UserValidation.class,
        UserRatingAction.class, CommentService.class, FrontServlet.class})
public class CommentServiceTest {
    String expectedPage;
    CommentService commentService;
    TransactionExecutor transactionExecutor;
    CommentDao commentDao;
    UserDao userDao;
    RequestContent content;
    MonsterService monsterService;
    HashMap<String, String[]> requestParameters;
    HashMap<String, Object> sessionAttributes;
    User user;
    Monster monster;

    @Before
    public void setUp() throws Exception {
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameters.put("star", new String[]{"1"});
        requestParameters.put("comment", new String[]{"test"});
        requestParameters.put("monsterId", new String[]{"1"});
        user = new User(1, new Role(1, "admin"),
                1, "test", "test", "test");
        sessionAttributes.put("user",user);
        monster = new Monster(-1,  "test",
                new Race(1, "test"),"test", 0.0,"test");
        sessionAttributes.put("monster", monster);
        commentService = new CommentService();
        content = mock(RequestContent.class);
        transactionExecutor = mock(TransactionExecutor.class);
        commentDao = mock(CommentDao.class);
        userDao = mock(UserDao.class);
        monsterService = mock(MonsterService.class);
        PowerMockito.mockStatic(CommentValidator.class);
        PowerMockito.mockStatic(UserValidation.class);
        PowerMockito.mockStatic(UserRatingAction.class);
    }

    @After
    public void tearDown() throws Exception {
        expectedPage = null;
        commentService = null;
        transactionExecutor = null;
        commentDao = null;
        userDao = null;
        content = null;
        monsterService = null;
        requestParameters = null;
        sessionAttributes = null;
        user = null;
        monster = null;
    }

    @Test
    public void addComment() throws Exception {
        expectedPage = "/jsp/monster.jsp";
        when(content.getRequestParameters()).thenReturn(requestParameters);
        when(content.getSessionAttributes()).thenReturn(sessionAttributes);
        when(CommentValidator.addCommentValidator(anyString(), anyString())).thenReturn(true);
        when(UserValidation.hasRoleAdminOrUser(any())).thenReturn(true);
        doReturn(new CommandResult(CommandResult.ResponseType.FORWARD, expectedPage)).when(monsterService).pickMonster(content);
        PowerMockito.whenNew(RequestContent.class).withAnyArguments().thenReturn(content);
        PowerMockito.whenNew(CommentDao.class).withAnyArguments().thenReturn(commentDao);
        PowerMockito.whenNew(TransactionExecutor.class).withNoArguments().thenReturn(transactionExecutor);
        PowerMockito.whenNew(MonsterService.class).withAnyArguments().thenReturn(monsterService);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        commentService.addComment(content);
        verify(content, times(2)).getRequestParameters();
        verify(content, times(3)).getSessionAttributes();
        assertEquals(CommandResult.ResponseType.FORWARD, commentService.addComment(content).getResponseType());
        assertEquals(expectedPage, commentService.addComment(content).getPage());
    }
}