package com.senchenko.aliens.command;

import com.senchenko.aliens.content.RequestContent;

public enum  CommandFactory {
    INSTANCE;

    public Command getCommand(RequestContent content){
        String name = content.getRequestParameters().get("command")[0];
        System.out.println(name);
        CommandType type = CommandType.valueOf(name.toUpperCase());
        return type.getCommand();
    }
}
