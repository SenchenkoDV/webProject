package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.dao.CommentDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.*;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.validation.CommentValidator;
import com.senchenko.aliens.validation.UserValidation;
import java.sql.Date;

public class CommentService implements Commentable{
    private static final int DEFAULT_ID = 0;
    private static final String MONSTER_ATTRIBUTE = "monster";
    private static final String USER_ATTRIBUTE = "user";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String NOT_ENOUGH_RIGHTS_ATTRIBUTE = "notEnoughRights";
    private static final String MONSTER_PAGE_PROPERTY = "monster";
    private static final String ERROR_PAGE_PROPERTY = "error";
    private static final String STAR_PARAMETER = "star";
    private static final String COMMENT_PARAMETER = "comment";
    private static final String INVALID_DATA_MESSAGE = "invalidData";

    public CommandResult addComment(RequestContent content){
        CommandResult commandResult;
        String mark = content.getRequestParameters().get(STAR_PARAMETER)[0];
        String comment = content.getRequestParameters().get(COMMENT_PARAMETER)[0];
        if (CommentValidator.addCommentValidator(mark, comment)){
            Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (UserValidation.hasRoleAdminOrUser(account)){
                CommentDao commentDao = SingletonDaoProvider.INSTANCE.getCommentDao();
                UserDao userDao = SingletonDaoProvider.INSTANCE.getUserDao();
                Comment currentComment = new Comment(DEFAULT_ID,
                        new Date(System.currentTimeMillis()),
                        (Monster) content.getSessionAttributes().get(MONSTER_ATTRIBUTE),
                        Integer.valueOf(mark),
                        comment,
                        (User)content.getSessionAttributes().get(USER_ATTRIBUTE));
                UserRatingAction.updateUserRating(currentComment);
                TransactionExecutor transactionExecutor = new TransactionExecutor();
                transactionExecutor.beginTransaction(commentDao, userDao);
                commentDao.create(currentComment);
                userDao.update(currentComment.getUser());
                transactionExecutor.commit();
                transactionExecutor.endTransaction();
                commandResult = new MonsterService().pickMonster(content);
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
                    PageManager.getProperty(MONSTER_PAGE_PROPERTY));
        }
        return commandResult;
    }
}
