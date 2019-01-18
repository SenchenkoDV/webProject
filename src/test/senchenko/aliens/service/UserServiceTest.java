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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.HashMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TransactionExecutor.class, RequestContent.class, CommentValidator.class, UserValidation.class, UserValidation.class,
         SingletonDaoProvider.class, UserDao.class, FrontServlet.class, ConnectionPool.class, UserService.class})
public class UserServiceTest {

    @Test
    public void goToLoginPage() {
        UserService userService =  spy(UserService.class);
        RequestContent content = mock(RequestContent.class);
        String expectedPage = "/jsp/login.jsp";
        assertEquals(CommandResult.ResponseType.FORWARD, userService.goToLoginPage(content).getResponseType());
        assertEquals(expectedPage, userService.goToLoginPage(content).getPage());

    }

    @Test
    public void goToRegistrationPage() {
        UserService userService =  spy(UserService.class);
        RequestContent content = mock(RequestContent.class);
        String expectedPage = "/jsp/registration.jsp";
        assertEquals(CommandResult.ResponseType.FORWARD, userService.goToRegistrationPage(content).getResponseType());
        assertEquals(expectedPage, userService.goToRegistrationPage(content).getPage());
    }

    @Test
    public void login() throws Exception {
        String expectedPage = "/jsp/monsters.jsp";
        UserService userService = new UserService();
        TransactionExecutor transactionExecutor = mock(TransactionExecutor.class);

        PowerMockito.mockStatic(UserValidation.class);
        UserDao userDao = mock(UserDao.class);
        MonsterService monsterService = mock(MonsterService.class);
        RequestContent content = mock(RequestContent.class);

        HashMap<String, String[]> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map1.put("login", new String[]{"test"});
        map1.put("password", new String[]{"pass"});
        User user = new User(1, new Role(1, "admin"), 1, "test", "test", "test");
        map2.put("user",user);

        when(monsterService.goToMonstersPage(content)).thenReturn(new CommandResult(CommandResult.ResponseType.FORWARD,expectedPage));
        when(userDao.findUserByLogin(anyString())).thenReturn(user);
        when(content.getRequestParameters()).thenReturn(map1);
        when(UserValidation.loginValidator(any(), any())).thenReturn(true);

        PowerMockito.whenNew(RequestContent.class).withAnyArguments().thenReturn(content);
        PowerMockito.whenNew(TransactionExecutor.class).withAnyArguments().thenReturn(transactionExecutor);
        PowerMockito.whenNew(UserDao.class).withAnyArguments().thenReturn(userDao);
        PowerMockito.whenNew(MonsterService.class).withAnyArguments().thenReturn(monsterService);

        assertEquals(CommandResult.ResponseType.FORWARD, userService.login(content).getResponseType());
        assertEquals(expectedPage, userService.login(content).getPage());

    }

    @Test
    public void logout() {
        UserService userService =  spy(UserService.class);
        RequestContent content = mock(RequestContent.class);
        String expectedPage = "/jsp/login.jsp";
        assertEquals(CommandResult.ResponseType.INVALIDATE, userService.logout(content).getResponseType());
        assertEquals(expectedPage, userService.logout(content).getPage());
    }

    @Test
    public void register() {
    }

    @Test
    public void changeRole() {
    }

    @Test
    public void goToUsersPage() {
    }
}