package test.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.service.UserService;
import com.senchenko.aliens.validation.CommentValidator;
import com.senchenko.aliens.validation.UserValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;


@RunWith(PowerMockRunner.class)
@PrepareForTest({CommentValidator.class, UserValidation.class})
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
    public void login() {
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