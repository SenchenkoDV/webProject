package com.senchenko.aliens.service;

import com.senchenko.aliens.dao.CommentDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.entity.Comment;
import java.util.List;

public class UserRatingAction {
    private static final int ACCEPTABLE_ESTIMATION_DIFFERENCE = 3;
    private static final int DEFAULT_RATING_INCREASE = 1;
    private static final int DEFAULT_RATING_DECREASE = 1;
    private static final int ENOUGH_MARKS_COUNT = 3;

    public static Comment updateUserRating(Comment comment){
        if (isEnoughMarks(comment.getMonster().getMonsterId()) ||
                ((comment.getMonster().getAverageRating() + ACCEPTABLE_ESTIMATION_DIFFERENCE) > comment.getMark() &&
                (comment.getMonster().getAverageRating() - ACCEPTABLE_ESTIMATION_DIFFERENCE) < comment.getMark())) {
            comment.getUser().setRating(comment.getUser().getRating() + DEFAULT_RATING_INCREASE);
        } else {
            comment.getUser().setRating(comment.getUser().getRating() + DEFAULT_RATING_DECREASE);
        }
        return comment;
    }

    private static boolean isEnoughMarks(int monsterId){
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        CommentDao commentDao = SingletonDaoProvider.INSTANCE.getCommentDao();
        transactionExecutor.beginTransaction(commentDao);
        List<Comment> commentList = commentDao.findAllByMonsterId(monsterId);
        transactionExecutor.commit();
        return commentList.size() >= ENOUGH_MARKS_COUNT;
    }
}
