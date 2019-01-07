package com.senchenko.aliens.command;

import com.senchenko.aliens.content.RequestContent;

public enum  CommandFactory {
    INSTANCE;
    private final static String DASH = "-";
    private final static String UNDERSCORE = "_";
    private final static String COMMAND = "command";

    public Command getCommand(RequestContent content){
        String name = content.getRequestParameters().get(COMMAND)[0].replaceAll(DASH, UNDERSCORE).trim();
        System.out.println(name);
        CommandType type = CommandType.valueOf(name.toUpperCase());
        return type.getCommand();
    }
}
