package com.senchenko.aliens.validation;

import java.util.regex.Pattern;

public class CommentValidator {
    private static final String MARK = "^(1|2|3|4|5|6|7|8|9|10)$";
    private static final String COMMENT = "^.{1,500}$";

    public static boolean addCommentValidator(String mark, String comment){
        return Pattern.matches(MARK, mark) &&
                Pattern.matches(COMMENT, comment) &&
                !CommonValidator.isXss(comment);
    }
}
