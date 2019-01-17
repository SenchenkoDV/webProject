package test.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.HashMap;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TransactionExecutor.class, RequestContent.class, CommentDao.class, CommentValidator.class, UserValidation.class,
        UserRatingAction.class, CommentService.class, SingletonDaoProvider.class})
public class CommentServiceTest {
    @Test
    public void addComment() throws Exception {
        String expectedPage = "/jsp/monster.jsp";
        CommentService commentService = spy(CommentService.class);
        TransactionExecutor transactionExecutor = mock(TransactionExecutor.class);
        CommentDao commentDao = mock(CommentDao.class);
        UserDao userDao = mock(UserDao.class);
        RequestContent content = mock(RequestContent.class);
        MonsterService monsterService = mock(MonsterService.class);
        PowerMockito.mockStatic(CommentValidator.class);
        PowerMockito.mockStatic(UserValidation.class);
        PowerMockito.mockStatic(UserRatingAction.class);

        HashMap<String, String[]> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map1.put("star", new String[]{"1"});
        map1.put("comment", new String[]{"test"});
        map1.put("monsterId", new String[]{"1"});
        User user = new User(1, new Role(1, "admin"), 1, "test", "test", "test");
        map2.put("user",user);
        Monster monster = new Monster(-1,  "test", new Race(1, "test"),"test", 0.0,"test");
        map2.put("monster", monster);

        PowerMockito.whenNew(CommentDao.class).withAnyArguments().thenReturn(commentDao);
        PowerMockito.whenNew(TransactionExecutor.class).withAnyArguments().thenReturn(transactionExecutor);
        PowerMockito.whenNew(MonsterService.class).withAnyArguments().thenReturn(monsterService);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);

        doReturn(new CommandResult(CommandResult.ResponseType.FORWARD, expectedPage)).when(monsterService).pickMonster(content);
        when(content.getRequestParameters()).thenReturn(map1);
        when(content.getSessionAttributes()).thenReturn(map2);
        when(CommentValidator.addCommentValidator(anyString(), anyString())).thenReturn(true);
        when(UserValidation.hasRoleAdminOrUser(any())).thenReturn(true);
        commentService.addComment(content);

        verify(commentService, times(1)).addComment(content);
        verify(content, times(2)).getRequestParameters();
        verify(content, times(3)).getSessionAttributes();
        assertEquals(CommandResult.ResponseType.FORWARD, commentService.addComment(content).getResponseType());
        assertEquals(expectedPage, commentService.addComment(content).getPage());
    }
}