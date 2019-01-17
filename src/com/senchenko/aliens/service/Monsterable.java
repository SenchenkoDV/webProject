package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.Content;
import com.senchenko.aliens.controller.RequestContent;

public interface Monsterable {
    CommandResult goToMonstersPage(RequestContent content);
    CommandResult pickMonster(Content content);
    CommandResult changeMonsterDescription(RequestContent content);
    CommandResult goToAddMonsterPage(RequestContent content);
    CommandResult addMonster(RequestContent content);
    CommandResult goToUpdateMonsterPage(RequestContent content);
    CommandResult updateMonster(RequestContent content);
}
