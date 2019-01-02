package com.senchenko.aliens.command;

import com.senchenko.aliens.service.MonsterService;
import com.senchenko.aliens.service.UserService;

public enum CommandType {
    LOGIN_PAGE(new UserService()::loginPage),
    REGISTRATION_PAGE(new UserService()::registrationPage),
    LOGIN(new UserService()::login),
    LOGOUT(new UserService()::logout),
    REGISTRATION(new UserService()::registration),
    CHANGE_RATING(new UserService()::changeRating),
    DISPLAY_USERS(new UserService()::displayAllUsers),
    MONSTERS(new MonsterService()::getMonstersList),
    MONSTER(new MonsterService()::getMonster),
    CHANGE_MONSTER_DESCRIPTION(new MonsterService()::changeMonsterDescription);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
