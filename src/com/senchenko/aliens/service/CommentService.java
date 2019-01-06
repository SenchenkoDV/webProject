package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.CommentDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.*;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.util.UserRatingAction;
import com.senchenko.aliens.validation.Validator;

import java.sql.Date;

public class CommentService {

    public CommandResult addComment(RequestContent content){
        CommandResult commandResult = null;
        String mark = content.getRequestParameters().get("star")[0];
        String comment = content.getRequestParameters().get("comment")[0];
        if (Validator.fakeValidator()){
            CommentDao commentDao = SingletonDaoProvider.INSTANCE.getCommentDao();
            UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
            Comment currentComment = new Comment(0,
                    new Date(System.currentTimeMillis()),
                    (Monster) content.getSessionAttributes().get("monster"),
                    Integer.valueOf(mark),
                    comment,
                    (User)content.getSessionAttributes().get("user"));
            UserRatingAction.updateUserRating(currentComment);
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(commentDao, userDao);
            commentDao.create(currentComment);
            userDao.update(currentComment.getUser());
            transactionExecutor.commit();
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("main"));
        }else {
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("errorPage"));
        }
        return commandResult;
    }
}
