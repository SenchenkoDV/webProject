package test.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.configuration.ConnectionPool;
import com.senchenko.aliens.controller.FrontServlet;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.Role;
import com.senchenko.aliens.entity.User;
import com.senchenko.aliens.service.MonsterService;
import com.senchenko.aliens.service.UserService;
import com.senchenko.aliens.validation.CommentValidator;
import com.senchenko.aliens.validation.UserValidation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TransactionExecutor.class, RequestContent.class, CommentValidator.class, UserValidation.class, UserValidation.class,
         SingletonDaoProvider.class, UserDao.class, FrontServlet.class, ConnectionPool.class, UserService.class})
public class UserServiceTest {
    HashMap<String, String[]> requestParameters;
    HashMap<String, Object> sessionAttributes;
    User user;
    UserService userService;
    TransactionExecutor transactionExecutor;
    UserDao userDao;
    MonsterService monsterService;
    RequestContent content;

    @Before
    public void setUp() throws Exception {
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameters.put("login", new String[]{"test"});
        requestParameters.put("password", new String[]{"pass"});
        requestParameters.put("email", new String[]{"email"});
        user = new User(1, new Role(1, "admin"),
                1, "test", "test", "test");
        sessionAttributes.put("user",user);
        PowerMockito.mockStatic(UserValidation.class);
        userService = new UserService();
        transactionExecutor = mock(TransactionExecutor.class);
        userDao = mock(UserDao.class);
        monsterService = mock(MonsterService.class);
        content = mock(RequestContent.class);
    }

    @After
    public void tearDown() throws Exception {
        user = null;
        requestParameters = null;
        sessionAttributes = null;
    }

    @Test
    public void goToLoginPage() {
        String expectedPage = "/jsp/login.jsp";
        assertEquals(CommandResult.ResponseType.FORWARD, userService.goToLoginPage(content).getResponseType());
        assertEquals(expectedPage, userService.goToLoginPage(content).getPage());
    }

    @Test
    public void goToRegistrationPage() {
        String expectedPage = "/jsp/registration.jsp";
        assertEquals(CommandResult.ResponseType.FORWARD, userService.goToRegistrationPage(content).getResponseType());
        assertEquals(expectedPage, userService.goToRegistrationPage(content).getPage());
    }

    @Test
    public void login() throws Exception {
        String expectedPage = "/../web?command=monsters";
        when(monsterService.goToMonstersPage(content))
                .thenReturn(new CommandResult(CommandResult.ResponseType.FORWARD,expectedPage));
        when(userDao.findUserByLogin(anyString())).thenReturn(user);
        when(content.getRequestParameters()).thenReturn(requestParameters);
        when(UserValidation.loginValidator(any(), any())).thenReturn(true);
        PowerMockito.whenNew(RequestContent.class).withAnyArguments().thenReturn(content);
        PowerMockito.whenNew(TransactionExecutor.class).withAnyArguments().thenReturn(transactionExecutor);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.whenNew(MonsterService.class).withAnyArguments().thenReturn(monsterService);
        assertEquals(CommandResult.ResponseType.REDIRECT, userService.login(content).getResponseType());
        assertEquals(expectedPage, userService.login(content).getPage());
    }

    @Test
    public void logout() {
        String expectedPage = "/jsp/login.jsp";
        assertEquals(CommandResult.ResponseType.INVALIDATE, userService.logout(content).getResponseType());
        assertEquals(expectedPage, userService.logout(content).getPage());
    }

    @Test
    public void register() throws Exception {
        String expectedPage = "/jsp/login.jsp";
        when(content.getRequestParameters()).thenReturn(requestParameters);
        when(UserValidation.registrationValidator(any(), any(), any())).thenReturn(true);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.whenNew(TransactionExecutor.class).withAnyArguments().thenReturn(transactionExecutor);
        assertEquals(CommandResult.ResponseType.FORWARD, userService.login(content).getResponseType());
        assertEquals(expectedPage, userService.login(content).getPage());
    }

    @Test
    public void changeRole() throws Exception {
        String expectedPage = "/jsp/users.jsp";
        when(content.getRequestParameters()).thenReturn(requestParameters);
        when(content.getSessionAttributes()).thenReturn(sessionAttributes);
        when(userDao.findById(anyInt())).thenReturn(user);
        when(UserValidation.hasRoleAdminOrUser(any())).thenReturn(true);
        when(UserValidation.hasRoleAdmin(any())).thenReturn(true);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.whenNew(TransactionExecutor.class).withAnyArguments().thenReturn(transactionExecutor);
        assertEquals(CommandResult.ResponseType.FORWARD, userService.login(content).getResponseType());
        assertEquals(expectedPage, userService.goToUsersPage(content).getPage());
    }

    @Test
    public void goToUsersPage() throws Exception {
        String expectedPage = "/jsp/users.jsp";
        when(content.getSessionAttributes()).thenReturn(sessionAttributes);
        when(UserValidation.hasRoleAdmin(any())).thenReturn(true);
        when(userDao.findAll()).thenReturn(new ArrayList<>());
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.whenNew(TransactionExecutor.class).withAnyArguments().thenReturn(transactionExecutor);
        assertEquals(CommandResult.ResponseType.FORWARD, userService.goToUsersPage(content).getResponseType());
        assertEquals(expectedPage, userService.goToUsersPage(content).getPage());
    }
}