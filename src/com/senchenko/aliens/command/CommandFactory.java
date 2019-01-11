package com.senchenko.aliens.command;

import com.senchenko.aliens.controller.RequestContent;

public enum  CommandFactory {
    INSTANCE;
    private static final String DASH = "-";
    private static final String UNDERSCORE = "_";
    private static final String COMMAND = "command";
    private static final String DEFAULT_COMMAND = "WRONG_COMMAND";
    private Command command;

    public Command getCommand(RequestContent content){
        String name = content.getRequestParameters().get(COMMAND)[0];
        if (name == null || name.isEmpty()) {
            command = CommandType.valueOf(DEFAULT_COMMAND).getCommand();
        }
        try {
            name = name.replaceAll(DASH, UNDERSCORE).trim();
            CommandType type = CommandType.valueOf(name.toUpperCase());
            command = type.getCommand();
        }catch (IllegalArgumentException e){
            command = CommandType.valueOf(DEFAULT_COMMAND).getCommand();
        }
        return command;
    }
}
