package com.senchenko.aliens.validation;

import java.util.regex.Pattern;

public class CommonValidator {
    private static final String SCRIPT_CHECK = "<script\\b[^<]*(?:(?!<\\/script>)<[^<]*)*<\\/script>";

    static public boolean fakeValidator(){
        return true;
    }

    public static boolean isXss(String text){
        return Pattern.matches(SCRIPT_CHECK, text);
    }
}
