package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.Role;
import com.senchenko.aliens.entity.User;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.validation.UserValidation;
import java.util.List;

public class UserService {
    private static final int DEFAULT_ID = 0;
    private static final int DEFAULT_USER_RATING = 0;
    private static final int ADMIN_ROLE = 1;
    private static final String DEFAULT_ROLE = "user";
    private static final String ERROR_LOGIN_PASS_ATTRIBUTE = "successfulCreateMonster";
    private static final String USER_ATTRIBUTE = "user";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String NOT_ENOUGH_RIGHTS_ATTRIBUTE = "notEnoughRights";
    private static final String LOGIN_PROPERTY = "login";
    private static final String REGISTRATION_PROPERTY = "registration";
    private static final String USERS_PROPERTY = "users";
    private static final String ERROR_PAGE_PROPERTY = "errorPage";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String ROLE_PARAMETER = "role";
    private static final String LOGIN_ERROR_MESSAGE = "loginError";
    private static final String CREATE_USER_ERROR_MESSAGE = "createUserError";
    private static final String INVALID_DATA_MESSAGE = "invalidData";

    public CommandResult showLoginPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT,
                PageManager.getProperty(LOGIN_PROPERTY));
    }

    public CommandResult showRegistrationPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT,
                PageManager.getProperty(REGISTRATION_PROPERTY));
    }

    public CommandResult login(RequestContent content){
        CommandResult commandResult = null;
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
                commandResult = new MonsterService().showMonstersPage(content);
            }
            else {
                content.getRequestAttributes().put(ERROR_LOGIN_PASS_ATTRIBUTE,
                        MessageManager.EN.getMessage(LOGIN_ERROR_MESSAGE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(LOGIN_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(LOGIN_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult logout(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.INVALIDATE,
                PageManager.getProperty(LOGIN_PROPERTY));
    }

    public CommandResult registration(RequestContent content){
        CommandResult commandResult = null;
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
                        MessageManager.EN.getMessage(CREATE_USER_ERROR_MESSAGE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(LOGIN_PROPERTY));
            }
            transactionExecutor.endTransaction();
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(REGISTRATION_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult changeRole(RequestContent content){
        CommandResult commandResult = null;
        String userId = content.getRequestParameters().get(USER_ID_PARAMETER)[0];
        String newRoleId = content.getRequestParameters().get(ROLE_PARAMETER)[0];
        if (UserValidation.changeRoleValidator(userId, newRoleId)){
            Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (( userRole != null) && (((User)userRole).getRole().getRoleId() == ADMIN_ROLE)){
                TransactionExecutor transactionExecutor = new TransactionExecutor();
                UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
                transactionExecutor.beginTransaction(userDao);
                User currentUser = (User) userDao.findById(Integer.parseInt(userId));
                transactionExecutor.commit();
                currentUser.setRole(new Role(Integer.parseInt(newRoleId), DEFAULT_ROLE));
                userDao.update(currentUser);
                transactionExecutor.commit();
                transactionExecutor.endTransaction();
                commandResult = displayAllUsers(content);
            }else {
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(USERS_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult displayAllUsers(RequestContent content){
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && (((User)userRole).getRole().getRoleId() == ADMIN_ROLE)){
            List<User> userList = null;
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
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }
}
