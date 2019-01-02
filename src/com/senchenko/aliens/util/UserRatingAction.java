package com.senchenko.aliens.util;

import com.senchenko.aliens.entity.Comment;
import com.senchenko.aliens.entity.User;

public class UserRatingAction {
    private static final int ACCEPTABLE_ESTIMATION_DIFFERENCE = 3;
    private static final int DEFAULT_RATING_INCREASE = 1;
    private static final int DEFAULT_RATING_DECREASE = 1;

    public static Comment updateUserRating(Comment comment){
        if (((comment.getMonster().getAverageRating() + ACCEPTABLE_ESTIMATION_DIFFERENCE) > comment.getMark() &&
                (comment.getMonster().getAverageRating() - ACCEPTABLE_ESTIMATION_DIFFERENCE) < comment.getMark())) {
            comment.getUser().setRating(comment.getUser().getRating() + DEFAULT_RATING_INCREASE);
        } else {
            comment.getUser().setRating(comment.getUser().getRating() + DEFAULT_RATING_DECREASE);
        }
        return comment;
    }
}
