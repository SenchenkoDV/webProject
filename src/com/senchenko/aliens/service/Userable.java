package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;

public interface Userable {
    CommandResult goToLoginPage(RequestContent content);
    CommandResult goToRegistrationPage(RequestContent content);
    CommandResult login(RequestContent content);
    CommandResult logout(RequestContent content);
    CommandResult register(RequestContent content);
    CommandResult changeRole(RequestContent content);
    CommandResult goToUsersPage(RequestContent content);
}
