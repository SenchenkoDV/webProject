package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.RequestContent;

public interface Commentable {
    CommandResult addComment(RequestContent content);
}
