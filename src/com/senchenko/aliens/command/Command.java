package com.senchenko.aliens.command;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent content);
}
