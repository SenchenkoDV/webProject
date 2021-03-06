package com.senchenko.aliens.command;

import com.senchenko.aliens.service.*;

public enum CommandType {
    LOGIN_PAGE(new UserService()::goToLoginPage),
    REGISTRATION_PAGE(new UserService()::goToRegistrationPage),
    LOGIN(new UserService()::login),
    LOGOUT(new UserService()::logout),
    REGISTRATION(new UserService()::register),
    CHANGE_RATING(new UserService()::changeRole),
    DISPLAY_USERS(new UserService()::goToUsersPage),
    MONSTERS(new MonsterService()::goToMonstersPage),
    MONSTER(new MonsterService()::pickMonster),
    CHANGE_MONSTER_DESCRIPTION(new MonsterService()::changeMonsterDescription),
    ADD_MONSTER_PAGE(new MonsterService()::goToAddMonsterPage),
    ADD_MONSTER(new MonsterService()::addMonster),
    UPDATE_MONSTER_PAGE(new  MonsterService()::goToUpdateMonsterPage),
    UPDATE_MONSTER(new MonsterService()::updateMonster),
    ADD_COMMENT(new CommentService()::addComment),
    LOCALE(new LocaleService()::changeLocale),
    WRONG_COMMAND(new ErrorService()::goToErrorPage);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
