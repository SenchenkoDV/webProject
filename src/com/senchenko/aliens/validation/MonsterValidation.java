package com.senchenko.aliens.validation;

import java.util.regex.Pattern;

public class MonsterValidation {
    private static final String INTEGER_NUMBERS = "^[1-9]\\d{0,19}$";
    private static final String NAME_OR_RACE = "^[a-zA-Z0-9 ]{1,20}$";
    private static final String DESCRIPTION = "^[\\s\\S]{1,2000}$";

    public static boolean showMonsterValidator(String monsterId){
        return Pattern.matches(INTEGER_NUMBERS, monsterId);
    }

    public static boolean changeMonsterDescriptionValidator(String changedMonsterDescription){
        return Pattern.matches(DESCRIPTION, changedMonsterDescription) &&
                !CommonValidator.isXss(changedMonsterDescription);
    }

    public static boolean addMonsterValidator(String name, String raceName, String description){
        return Pattern.matches(NAME_OR_RACE, name) &&
                Pattern.matches(NAME_OR_RACE, raceName) &&
                Pattern.matches(DESCRIPTION, description) &&
                !CommonValidator.isXss(name) &&
                !CommonValidator.isXss(raceName) &&
                !CommonValidator.isXss(description);

    }

    public static boolean updateMonsterValidator(String monsterId, String name, String raceName, String description){
        return Pattern.matches(INTEGER_NUMBERS, monsterId) &&
                Pattern.matches(NAME_OR_RACE, name) &&
                Pattern.matches(NAME_OR_RACE, raceName) &&
                Pattern.matches(DESCRIPTION, description) &&
                !CommonValidator.isXss(name) &&
                !CommonValidator.isXss(raceName) &&
                !CommonValidator.isXss(description);
    }

    public static boolean showUpdateMonsterPageValidator(String name){
        return Pattern.matches(NAME_OR_RACE, name) &&
                !CommonValidator.isXss(name);
    }
}
