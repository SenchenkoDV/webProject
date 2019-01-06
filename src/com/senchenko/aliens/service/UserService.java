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
import com.sun.istack.internal.NotNull;

import java.util.List;

public class UserService {
    private static final Role DEFAULT_USER_ROLE = new Role(2, "user");
    private static final int DEFAULT_USER_ID = 0;
    private static final int DEFAULT_USER_RATING = 0;

    public CommandResult showLoginPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty("login"));
    }

    public CommandResult showRegistrationPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty("registration"));
    }

    public CommandResult login(RequestContent content){
        CommandResult commandResult = null;
        String[] enterLogin = content.getRequestParameters().get("login");
        String[] enterPass = content.getRequestParameters().get("password");

        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(userDao);
        User currentUser = (User) userDao.findUserByLogin(enterLogin[0]);
        transactionExecutor.commit();
        if (enterLogin == null || enterPass == null || currentUser == null || (currentUser.getLogin().equals(enterLogin) &&
                currentUser.getPassword().equals(enterPass))){
            content.getRequestParameters().put("errorLoginPassMessage", new String[]{MessageManager.EN.getMessage("loginError")});
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("login"));
        }
        else {
            content.getSessionAttributes().put("user", currentUser);
            commandResult = new MonsterService().getMonstersList(content);
        }
        return commandResult;
    }

    public CommandResult logout(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.INVALIDATE, PageManager.getProperty("index"));
    }

    public CommandResult registration(RequestContent content){
        System.out.println("registration start");
        CommandResult commandResult = null;
        String[] enterLogin = content.getRequestParameters().get("login");
        String[] enterPass = content.getRequestParameters().get("password");
        String[] enterEmail = content.getRequestParameters().get("email");

        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(userDao);
        User createdUser = (User) userDao.findUserByLogin(enterLogin[0]);
        transactionExecutor.commit();

        if (createdUser == null) {
            createdUser = new User(DEFAULT_USER_ID, DEFAULT_USER_ROLE, DEFAULT_USER_RATING,
                    enterLogin[0], enterPass[0], enterEmail[0]);
            transactionExecutor.beginTransaction(userDao);
            userDao.create(createdUser);
            transactionExecutor.commit();
            commandResult = login(content);
        }else {
            content.getRequestParameters().put("result", new String[]{createdUser.getLogin() +
                    MessageManager.EN.getMessage("createUserError")});
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("index"));
        }
        return commandResult;
    }

    public CommandResult changeRating(RequestContent content){
        int userId;
        int newRoleId;
        userId = Integer.parseInt(content.getRequestParameters().get("userId")[0]);
        newRoleId = Integer.parseInt(content.getRequestParameters().get("role")[0]);
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        transactionExecutor.beginTransaction(userDao);
        User currentUser = (User) userDao.findById(userId);
        transactionExecutor.commit();
        currentUser.setRole(new Role(newRoleId, "user"));
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
        content.getSessionAttributes().put("users", userList);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("users"));
        return commandResult;
    }
}
