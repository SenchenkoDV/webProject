package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.CommentDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.dao.UserDao;
import com.senchenko.aliens.entity.*;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.util.UserRatingAction;
import com.senchenko.aliens.validation.Validator;

import java.sql.Date;

public class CommentService {
    private static final int DEFAULT_ID = 0;
    private static final int ADMIN_ROLE = 1;
    private static final int USER_ROLE = 2;
    private static final String MONSTER_ATTRIBUTE = "monster";
    private static final String USER_ATTRIBUTE = "user";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String NOT_ENOUGH_RIGHTS_ATTRIBUTE = "notEnoughRights";
    private static final String MONSTER_PAGE_PROPERTY = "monsterPage";
    private static final String ERROR_PAGE_PROPERTY = "errorPage";
    private static final String STAR_PARAMETER = "star";
    private static final String COMMENT_PARAMETER = "comment";

    public CommandResult addComment(RequestContent content){
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && ((((User)userRole).getRole().getRoleId() == ADMIN_ROLE) ||
                (((User)userRole).getRole().getRoleId() == USER_ROLE))){
            String mark = content.getRequestParameters().get(STAR_PARAMETER)[0];
            String comment = content.getRequestParameters().get(COMMENT_PARAMETER)[0];
            if (Validator.fakeValidator()){
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
                commandResult = new MonsterService().getMonster(content);
            }else {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }
}
