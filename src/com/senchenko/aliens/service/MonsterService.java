package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.MonsterDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;

import java.util.List;

public enum MonsterService {
    INSTANCE;

    public CommandResult getMonstersList (RequestContent content){
        CommandResult commandResult = null;
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(monsterDao);
        List<Monster> monsters = monsterDao.findAll();
        transactionExecutor.commit();
        content.getSessionAttributes().put("monsters", monsters);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("monsters"));
        return commandResult;
    }
}
