package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.MonsterDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.manager.PageManager;
import java.util.List;

public class MonsterService {

    public CommandResult getMonstersList(RequestContent content){
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

    public CommandResult getMonster(RequestContent content){
        CommandResult commandResult = null;
        Monster currentMonster = null;
        String monsterName = content.getRequestParameters().get("name")[0];
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(monsterDao);
        currentMonster = (Monster) monsterDao.findByName(monsterName);
        transactionExecutor.commit();
        content.getSessionAttributes().put("monster", currentMonster);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("main"));
        return commandResult;
    }

    public CommandResult changeMonsterDescription(RequestContent content){
        CommandResult commandResult = null;
        String changedMonsterDescription = content.getRequestParameters().get("description")[0];
        Monster currentMonster = (Monster) content.getSessionAttributes().get("monster");
        currentMonster.setDescription(changedMonsterDescription);
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(monsterDao);
        monsterDao.update(currentMonster);
        transactionExecutor.commit();
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("main"));
        return commandResult;
    }
}
