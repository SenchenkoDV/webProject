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
import java.sql.Date;

public class CommentService {
    public CommandResult addComment(RequestContent content){
        CommandResult commandResult = null;
        int mark = Integer.parseInt(content.getRequestParameters().get("mark")[0]);
        String comment = content.getRequestParameters().get("comment")[0];
        CommentDao commentDao = SingletonDaoProvider.INSTANCE.getCommentDao();
        UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
        Comment currentComment = new Comment(0,
                new Date(System.currentTimeMillis()),
                (Monster) content.getSessionAttributes().get("monster"),
                mark,
                comment,
                (User)content.getSessionAttributes().get("user"));
        UserRatingAction.updateUserRating(currentComment);
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(commentDao);
        transactionExecutor.beginTransaction(userDao);
        commentDao.create(currentComment);
        userDao.update(currentComment.getUser());
        transactionExecutor.commit();
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("users"));
        return commandResult;
    }
}
