package com.senchenko.aliens.service;

import com.senchenko.aliens.content.CommandResult;
import com.senchenko.aliens.content.RequestContent;
import com.senchenko.aliens.dao.*;
import com.senchenko.aliens.entity.Comment;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.entity.Race;
import com.senchenko.aliens.entity.User;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;
import java.util.List;

public class MonsterService {
    private static final int DEFAULT_ID = 0;
    private static final int ADMIN_ROLE = 1;
    private static final int USER_ROLE = 2;
    private static final Double DEFAULT_AVERAGE_RATING = 0.0;
    private static final String USER_ATTRIBUTE = "user";
    private static final String MONSTERS_ATTRIBUTE = "monsters";
    private static final String MONSTER_ATTRIBUTE = "monster";
    private static final String COMMENTS_ATTRIBUTE = "comments";
    private static final String FILE_PATH_ATTRIBUTE = "filePath";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String NOT_ENOUGH_RIGHTS_ATTRIBUTE = "notEnoughRights";
    private static final String MONSTERS_PROPERTY = "monsters";
    private static final String MONSTER_PAGE_PROPERTY = "monsterPage";
    private static final String ADD_MONSTER_PROPERTY = "addMonster";
    private static final String UPDATE_MONSTER_PROPERTY = "updateMonster";
    private static final String ERROR_PAGE_PROPERTY = "errorPage";
    private static final String MONSTERS_ID_PARAMETER = "monsterId";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String NAME_PARAMETER = "name";
    private static final String RACE_PARAMETER = "race";
    private static final String SUCCESSFUL_CREATE_MONSTER_MESSAGE = "successfulCreateMonster";
    private static final String SUCCESSFUL_UPDATE_MONSTER_MESSAGE = "successfulUpdateMonster";

    public CommandResult showMonstersPage(RequestContent content){
        CommandResult commandResult = null;
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(monsterDao);
        List<Monster> monsters = monsterDao.findAll();
        transactionExecutor.commit();
        transactionExecutor.endTransaction();
        content.getSessionAttributes().put(MONSTERS_ATTRIBUTE, monsters);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(MONSTERS_PROPERTY));
        return commandResult;
    }

    public CommandResult getMonster(RequestContent content){
        CommandResult commandResult = null;
        Monster currentMonster = null;
        List<Comment> comments = null;
        int monsterId = Integer.parseInt(content.getRequestParameters().get(MONSTERS_ID_PARAMETER)[0]);
        MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
        CommentDao commentDao = SingletonDaoProvider.INSTANCE.getCommentDao();
        TransactionExecutor transactionExecutor = new TransactionExecutor();
        transactionExecutor.beginTransaction(monsterDao, commentDao);
        currentMonster = (Monster) monsterDao.findById(monsterId);
        comments = commentDao.findAllByMonsterId(monsterId);
        transactionExecutor.commit();
        transactionExecutor.endTransaction();
        content.getSessionAttributes().put(MONSTER_ATTRIBUTE, currentMonster);
        content.getSessionAttributes().put(COMMENTS_ATTRIBUTE, comments);
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(MONSTER_PAGE_PROPERTY));
        return commandResult;
    }

    public CommandResult changeMonsterDescription(RequestContent content){
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && ((((User)userRole).getRole().getRoleId() == ADMIN_ROLE) ||
                (((User)userRole).getRole().getRoleId() == USER_ROLE))){
            String changedMonsterDescription = content.getRequestParameters().get(DESCRIPTION_PARAMETER)[0];
            Monster currentMonster = (Monster) content.getSessionAttributes().get(MONSTER_ATTRIBUTE);
            currentMonster.setDescription(changedMonsterDescription);
            MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(monsterDao);
            monsterDao.update(currentMonster);
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(MONSTER_PAGE_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult addMonsterPage(RequestContent content){
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && (((User)userRole).getRole().getRoleId() == ADMIN_ROLE)){
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT,
                PageManager.getProperty(ADD_MONSTER_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult addMonster(RequestContent content){
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && (((User)userRole).getRole().getRoleId() == ADMIN_ROLE)){
            String picturePath = (String) content.getRequestAttributes().get(FILE_PATH_ATTRIBUTE);
            String name = content.getRequestParameters().get(NAME_PARAMETER)[0];
            String description = content.getRequestParameters().get(DESCRIPTION_PARAMETER)[0];
            String raceName = content.getRequestParameters().get(RACE_PARAMETER)[0];
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            RaceDao raceDao = SingletonDaoProvider.INSTANCE.getRaceDao();
            transactionExecutor.beginTransaction(raceDao);
            Race race = (Race) raceDao.findByRace(raceName);
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            if (race == null){
                raceDao.create(new Race(DEFAULT_ID, raceName));
                transactionExecutor.commit();
            }
            MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
            transactionExecutor.beginTransaction(monsterDao);
            monsterDao.create(new Monster(DEFAULT_ID, name, race, description,
                    DEFAULT_AVERAGE_RATING, picturePath));
            transactionExecutor.commit();
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(SUCCESSFUL_CREATE_MONSTER_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT,
                    PageManager.getProperty(ADD_MONSTER_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult showUpdateMonsterPage(RequestContent content) {
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && (((User)userRole).getRole().getRoleId() == ADMIN_ROLE)){
            MonsterDao monsterDao = new MonsterDao();
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(monsterDao);
            Monster currentMonster = (Monster) monsterDao.findByName(content.getRequestParameters().get(NAME_PARAMETER)[0]);
            content.getSessionAttributes().put(MONSTER_ATTRIBUTE, currentMonster);
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, PageManager.getProperty(UPDATE_MONSTER_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult updateMonster(RequestContent content){
        CommandResult commandResult = null;
        Object userRole = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (( userRole != null) && (((User)userRole).getRole().getRoleId() == ADMIN_ROLE)){
            int updatedMonsterId = Integer.parseInt(content.getRequestParameters().get(MONSTERS_ID_PARAMETER)[0]);
            String picturePath = (String) content.getRequestAttributes().get(FILE_PATH_ATTRIBUTE);
            String name = content.getRequestParameters().get(NAME_PARAMETER)[0];
            String description = content.getRequestParameters().get(DESCRIPTION_PARAMETER)[0];
            String raceName = content.getRequestParameters().get(RACE_PARAMETER)[0];
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            RaceDao raceDao = SingletonDaoProvider.INSTANCE.getRaceDao();
            transactionExecutor.beginTransaction(raceDao);
            Race race = (Race) raceDao.findByRace(raceName);
            transactionExecutor.commit();
            if (race == null){
                raceDao.create(new Race(DEFAULT_ID, raceName));
                transactionExecutor.commit();
            }
            MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
            transactionExecutor.beginTransaction(monsterDao);
            monsterDao.update(new Monster(updatedMonsterId, name, race, description, DEFAULT_AVERAGE_RATING, picturePath));
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            content.getSessionAttributes().put(RESULT_ATTRIBUTE, MessageManager.EN.getMessage(SUCCESSFUL_UPDATE_MONSTER_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageManager.getProperty(UPDATE_MONSTER_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.EN.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }
}
