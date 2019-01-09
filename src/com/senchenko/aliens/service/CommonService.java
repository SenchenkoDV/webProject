package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;

public class CommonService {
    private static final String ERROR_PAGE_PROPERTY = "errorPage";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String WRONG_ACTION_ATTRIBUTE = "wrongAction";

    public CommandResult errorCommandType(RequestContent content){
        content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                MessageManager.EN.getMessage(WRONG_ACTION_ATTRIBUTE));
        return new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(ERROR_PAGE_PROPERTY));
    }
}
