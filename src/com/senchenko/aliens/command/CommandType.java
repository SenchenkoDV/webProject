package com.senchenko.aliens.command;

import com.senchenko.aliens.service.CommentService;
import com.senchenko.aliens.service.MonsterService;
import com.senchenko.aliens.service.UserService;

public enum CommandType {
    LOGIN_PAGE(new UserService()::showLoginPage),
    REGISTRATION_PAGE(new UserService()::showRegistrationPage),
    LOGIN(new UserService()::login),
    LOGOUT(new UserService()::logout),
    REGISTRATION(new UserService()::registration),
    CHANGE_RATING(new UserService()::changeRole),
    DISPLAY_USERS(new UserService()::displayAllUsers),
    MONSTERS(new MonsterService()::showMonstersPage),
    MONSTER(new MonsterService()::getMonster),
    CHANGE_MONSTER_DESCRIPTION(new MonsterService()::changeMonsterDescription),
    ADD_MONSTER_PAGE(new MonsterService()::addMonsterPage),
    ADD_MONSTER(new MonsterService()::addMonster),
    UPDATE_MONSTER_PAGE(new  MonsterService()::showUpdateMonsterPage),
    UPDATE_MONSTER(new MonsterService()::updateMonster),
    ADD_COMMENT(new CommentService()::addComment);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
