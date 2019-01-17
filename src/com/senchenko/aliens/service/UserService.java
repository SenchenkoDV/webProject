package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.Role;
import com.senchenko.aliens.entity.User;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.validation.UserValidation;
import java.util.List;

public class UserService implements Userable{
    private static final int DEFAULT_ID = 0;
    private static final int DEFAULT_USER_RATING = 0;
    private static final String DEFAULT_ROLE = "user";
    private static final String ERROR_LOGIN_PASS_ATTRIBUTE = "successfulCreateMonster";
    private static final String USER_ATTRIBUTE = "user";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String NOT_ENOUGH_RIGHTS_ATTRIBUTE = "notEnoughRights";
    private static final String PASSWORD_REQUIREMENTS_ATTRIBUTE = "requirements";
    private static final String LOGIN_PROPERTY = "login";
    private static final String REGISTRATION_PROPERTY = "registration";
    private static final String USERS_PROPERTY = "users";
    private static final String ERROR_PAGE_PROPERTY = "error";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String ROLE_PARAMETER = "role";
    private static final String LOGIN_ERROR_MESSAGE = "loginError";
    private static final String CREATE_USER_ERROR_MESSAGE = "createUserError";
    private static final String INVALID_DATA_MESSAGE = "invalidData";
    private static final String PASSWORD_REQUIREMENTS_MESSAGE  = "passwordRequirements";

    public CommandResult goToLoginPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(LOGIN_PROPERTY));
    }

    public CommandResult goToRegistrationPage(RequestContent content){
        content.getSessionAttributes().put(PASSWORD_REQUIREMENTS_ATTRIBUTE,
                MessageManager.getMessage(PASSWORD_REQUIREMENTS_MESSAGE));
        return new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(REGISTRATION_PROPERTY));
    }

    public CommandResult login(RequestContent content){
        CommandResult commandResult;
        String enterLogin = content.getRequestParameters().get(LOGIN_PARAMETER)[0];
        String enterPass = content.getRequestParameters().get(PASSWORD_PARAMETER)[0];
        if (UserValidation.loginValidator(enterLogin, enterPass)){
            UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(userDao);
            User currentUser = (User) userDao.findUserByLogin(enterLogin);
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            if (currentUser != null || (currentUser.getLogin().equals(enterLogin) &&
                    currentUser.getPassword().equals(enterPass))){
                content.getSessionAttributes().put(USER_ATTRIBUTE, currentUser);
                commandResult = new MonsterService().goToMonstersPage(content);
            }
            else {
                content.getRequestAttributes().put(ERROR_LOGIN_PASS_ATTRIBUTE,
                        MessageManager.getMessage(LOGIN_ERROR_MESSAGE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(LOGIN_PROPERTY));
            }
        }else {
            content.getRequestAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(LOGIN_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult logout(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.INVALIDATE,
                PageManager.getProperty(LOGIN_PROPERTY));
    }

    public CommandResult register(RequestContent content){
        CommandResult commandResult;
        String enterLogin = content.getRequestParameters().get(LOGIN_PARAMETER)[0];
        String enterPass = content.getRequestParameters().get(PASSWORD_PARAMETER)[0];
        String enterEmail = content.getRequestParameters().get(EMAIL_PARAMETER)[0];
        if (UserValidation.registrationValidator(enterLogin, enterPass, enterEmail)){
            UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(userDao);
            User createdUser = (User) userDao.findUserByLogin(enterLogin);
            transactionExecutor.commit();
            if (createdUser == null) {
                createdUser = new User(DEFAULT_ID, new Role(DEFAULT_ID, DEFAULT_ROLE), DEFAULT_USER_RATING,
                        enterLogin, enterPass, enterEmail);
                transactionExecutor.beginTransaction(userDao);
                userDao.create(createdUser);
                transactionExecutor.commit();
                commandResult = login(content);
            }else {
                content.getRequestAttributes().put(RESULT_ATTRIBUTE, createdUser.getLogin() +
                        MessageManager.getMessage(CREATE_USER_ERROR_MESSAGE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(LOGIN_PROPERTY));
            }
            transactionExecutor.endTransaction();
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(REGISTRATION_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult changeRole(RequestContent content){
        CommandResult commandResult;
        String userId = content.getRequestParameters().get(USER_ID_PARAMETER)[0];
        String newRoleId = content.getRequestParameters().get(ROLE_PARAMETER)[0];
        if (UserValidation.changeRoleValidator(userId, newRoleId)){
            Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (UserValidation.hasRoleAdmin(account)){
                TransactionExecutor transactionExecutor = new TransactionExecutor();
                UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
                transactionExecutor.beginTransaction(userDao);
                User currentUser = (User) userDao.findById(Integer.parseInt(userId));
                transactionExecutor.commit();
                currentUser.setRole(new Role(Integer.parseInt(newRoleId), DEFAULT_ROLE));
                userDao.update(currentUser);
                transactionExecutor.commit();
                transactionExecutor.endTransaction();
                commandResult = goToUsersPage(content);
            }else {
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(USERS_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult goToUsersPage(RequestContent content){
        CommandResult commandResult;
        Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (UserValidation.hasRoleAdmin(account)){
            List<User> userList;
            UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(userDao);
            userList = userDao.findAll();
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            content.getSessionAttributes().put(USERS_ATTRIBUTE, userList);
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(USERS_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }
}
