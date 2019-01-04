package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.MonsterDao;
import com.senchenko.aliens.dao.RaceDao;
import com.senchenko.aliens.dao.SingletonDaoProvider;
import com.senchenko.aliens.dao.TransactionExecutor;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.entity.Race;
import com.senchenko.aliens.manager.MessageManager;
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

    public CommandResult showMonsterPage(RequestContent content){
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty("addMonster"));
    }

    public CommandResult addMonster(RequestContent content){
        CommandResult commandResult = null;
        String picturePath = (String) content.getRequestAttributes().get("filePath");
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
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        transactionExecutor.beginTransaction(monsterDao);
        monsterDao.create(new Monster(0, name, race, description, 0., picturePath));
        transactionExecutor.commit();
        content.getSessionAttributes().put("result", MessageManager.EN.getMessage("successfulCreateMonster"));
        commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty("addMonster"));
        return commandResult;
    }

    public CommandResult showUpdateMonsterPage(RequestContent content){
        MonsterDao monsterDao = new MonsterDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(monsterDao);
        Monster currentMonster = (Monster) monsterDao.findByName(content.getRequestParameters().get("name")[0]);
        content.getSessionAttributes().put("monster", currentMonster);
        transactionExecutor.commit();
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty("updateMonster"));
    }

    public CommandResult updateMonster(RequestContent content){
        CommandResult commandResult = null;
        int updatedMonsterId = Integer.parseInt(content.getRequestParameters().get("monster-id")[0]);
        String picturePath = (String) content.getRequestAttributes().get("filePath");
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
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        transactionExecutor.beginTransaction(monsterDao);
        monsterDao.update(new Monster(updatedMonsterId, name, race, description, 0., picturePath));
        transactionExecutor.commit();
        content.getSessionAttributes().put("result", MessageManager.EN.getMessage("successfulUpdateMonster"));
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty("updateMonster"));
        return commandResult;
    }

}
