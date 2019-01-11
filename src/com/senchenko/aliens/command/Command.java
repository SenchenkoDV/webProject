package com.senchenko.aliens.command;

import com.senchenko.aliens.controller.RequestContent;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent content);
}
