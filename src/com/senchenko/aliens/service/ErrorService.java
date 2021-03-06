package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;

public class ErrorService {
    private static final String ERROR_PAGE_PROPERTY = "error";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String WRONG_ACTION_ATTRIBUTE = "wrongAction";

    public CommandResult goToErrorPage(RequestContent content){
        content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                MessageManager.getMessage(WRONG_ACTION_ATTRIBUTE));
        return new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(ERROR_PAGE_PROPERTY));
    }
}
