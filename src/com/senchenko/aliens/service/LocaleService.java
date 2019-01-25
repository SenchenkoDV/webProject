package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;

public class LocaleService {
    private static final String EMPTY_PAGE = "";
    private static final String LOCALE_PARAMETER = "locale";
    private static final String LANGUAGE_ATTRIBUTE = "language";

    public CommandResult changeLocale(RequestContent content){
        String selectedLocale = content.getRequestParameters().get(LOCALE_PARAMETER)[0];
        content.getSessionAttributes().put(LANGUAGE_ATTRIBUTE, selectedLocale);
        return new CommandResult(CommandResult.ResponseType.FORWARD, "monsters");
    }
}