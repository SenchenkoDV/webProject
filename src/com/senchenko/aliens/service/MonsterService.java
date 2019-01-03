package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.MonsterDao;
import com.senchenko.aliens.dao.RaceDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.entity.Race;
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

    public CommandResult addMonsterPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty("addMonster"));
    }

    public CommandResult addMonster(RequestContent content){
        CommandResult commandResult = null;
        String picturePath = (String) content.getRequestAttributes().get("filePath");
        System.out.println(picturePath);
        String name = content.getRequestParameters().get("name")[0];
        String description = content.getRequestParameters().get("description")[0];
        String raceName = content.getRequestParameters().get("race")[0];
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        RaceDao raceDao = SingletonDaoProvider.INSTANCE.getRaceDao();
        transactionExecutor.beginTransaction(raceDao);
        Race race = (Race) raceDao.findByRace(raceName);
        transactionExecutor.commit();
        if (race == null){
            raceDao.create(new Race(0, raceName));
            transactionExecutor.commit();
        }
        System.out.println(race);
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        transactionExecutor.beginTransaction(monsterDao);
        monsterDao.create(new Monster(0, name, race, description, 0., picturePath));
        transactionExecutor.commit();
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("addMonster"));
        return commandResult;
    }
}
