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
import java.util.List;

public class UserService {
    private static final int DEFAULT_ID = 0;
    private static final int DEFAULT_USER_RATING = 0;
    private static final String DEFAULT_ROLE = "user";
    private static final String ERROR_LOGIN_PASS_ATTRIBUTE = "successfulCreateMonster";
    private static final String USER_ATTRIBUTE = "user";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String LOGIN_PROPERTY = "login";
    private static final String REGISTRATION_PROPERTY = "registration";
    private static final String USERS_PROPERTY = "users";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String ROLE_PARAMETER = "role";
    private static final String LOGIN_ERROR_MESSAGE = "successfulCreateMonster";
    private static final String CREATE_USER_ERROR_MESSAGE = "createUserError";

    public CommandResult showLoginPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty(LOGIN_PROPERTY));
    }

    public CommandResult showRegistrationPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty(REGISTRATION_PROPERTY));
    }

    public CommandResult login(RequestContent content){
        CommandResult commandResult = null;
        String[] enterLogin = content.getRequestParameters().get(LOGIN_PARAMETER);
        String[] enterPass = content.getRequestParameters().get(PASSWORD_PARAMETER);

        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(userDao);
        User currentUser = (User) userDao.findUserByLogin(enterLogin[0]);
        transactionExecutor.commit();
        if (enterLogin == null || enterPass == null || currentUser == null || (currentUser.getLogin().equals(enterLogin) &&
                currentUser.getPassword().equals(enterPass))){
            content.getRequestAttributes().put(ERROR_LOGIN_PASS_ATTRIBUTE, MessageManager.EN.getMessage(LOGIN_ERROR_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty(LOGIN_PROPERTY));
        }
        else {
            content.getSessionAttributes().put(USER_ATTRIBUTE, currentUser);
            commandResult = new MonsterService().showMonstersPage(content);
        }
        return commandResult;
    }

    public CommandResult logout(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.INVALIDATE, PageManager.getProperty(LOGIN_PROPERTY));
    }

    public CommandResult registration(RequestContent content){
        CommandResult commandResult = null;
        String[] enterLogin = content.getRequestParameters().get(LOGIN_PARAMETER);
        String[] enterPass = content.getRequestParameters().get(PASSWORD_PARAMETER);
        String[] enterEmail = content.getRequestParameters().get(EMAIL_PARAMETER);

        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(userDao);
        User createdUser = (User) userDao.findUserByLogin(enterLogin[0]);
        transactionExecutor.commit();

        if (createdUser == null) {
            createdUser = new User(DEFAULT_ID, new Role(DEFAULT_ID, DEFAULT_ROLE), DEFAULT_USER_RATING,
                    enterLogin[0], enterPass[0], enterEmail[0]);
            transactionExecutor.beginTransaction(userDao);
            userDao.create(createdUser);
            transactionExecutor.commit();
            commandResult = login(content);
        }else {
            content.getRequestAttributes().put(RESULT_ATTRIBUTE, createdUser.getLogin() +
                    MessageManager.EN.getMessage(CREATE_USER_ERROR_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty(LOGIN_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult changeRole(RequestContent content){
        int userId;
        int newRoleId;
        userId = Integer.parseInt(content.getRequestParameters().get(USER_ID_PARAMETER)[0]);
        newRoleId = Integer.parseInt(content.getRequestParameters().get(ROLE_PARAMETER)[0]);
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        transactionExecutor.beginTransaction(userDao);
        User currentUser = (User) userDao.findById(userId);
        transactionExecutor.commit();
        currentUser.setRole(new Role(newRoleId, DEFAULT_ROLE));
        userDao.update(currentUser);
        transactionExecutor.commit();
        return displayAllUsers(content);
    }

    public CommandResult displayAllUsers(RequestContent content){
        CommandResult commandResult = null;
        List<User> userList = null;
        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(userDao);
        userList = userDao.findAll();
        transactionExecutor.commit();
        content.getSessionAttributes().put(USERS_ATTRIBUTE, userList);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty(USERS_PROPERTY));
        return commandResult;
    }
}
