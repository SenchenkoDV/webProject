package com.senchenko.aliens.command;

import com.senchenko.aliens.service.MonsterService;
import com.senchenko.aliens.service.UserService;

public enum CommandType {
    LOGIN(new UserService()::login),
    LOGOUT(new UserService()::logout),
    REGISTRATION(new UserService()::registration),
    MONSTERS(new MonsterService()::getMonstersList),
    MONSTER(new MonsterService()::getMonster),
    CHANGE_MONSTER_DESCRIPTION(new MonsterService()::getMonstersList);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
