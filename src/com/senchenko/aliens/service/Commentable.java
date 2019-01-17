package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.Content;

public interface Commentable {
    CommandResult addComment(Content content);
}
