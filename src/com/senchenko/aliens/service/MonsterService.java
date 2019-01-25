package com.senchenko.aliens.service;

import com.senchenko.aliens.command.CommandResult;
import com.senchenko.aliens.controller.Content;
import com.senchenko.aliens.controller.RequestContent;
import com.senchenko.aliens.dao.*;
import com.senchenko.aliens.entity.Comment;
import com.senchenko.aliens.entity.Monster;
import com.senchenko.aliens.entity.Race;
import com.senchenko.aliens.manager.MessageManager;
import com.senchenko.aliens.manager.PageManager;
import com.senchenko.aliens.validation.MonsterValidation;
import com.senchenko.aliens.validation.UserValidation;
import java.util.List;

public class MonsterService implements Monsterable{
    private static final int DEFAULT_ID = 0;
    private static final Double DEFAULT_AVERAGE_RATING = 0.0;
    private static final String USER_ATTRIBUTE = "user";
    private static final String MONSTERS_ATTRIBUTE = "monsters";
    private static final String MONSTER_ATTRIBUTE = "monster";
    private static final String COMMENTS_ATTRIBUTE = "comments";
    private static final String FILE_PATH_ATTRIBUTE = "filePath";
    private static final String RESULT_ATTRIBUTE = "result";
    private static final String NOT_ENOUGH_RIGHTS_ATTRIBUTE = "notEnoughRights";
    private static final String MONSTERS_PROPERTY = "monsters";
    private static final String MONSTER_PAGE_PROPERTY = "monster";
    private static final String ADD_MONSTER_PROPERTY = "addMonster";
    private static final String UPDATE_MONSTER_PROPERTY = "updateMonster";
    private static final String ERROR_PAGE_PROPERTY = "error";
    private static final String MONSTERS_ID_PARAMETER = "monsterId";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String NAME_PARAMETER = "name";
    private static final String RACE_PARAMETER = "race";
    private static final String SUCCESSFUL_CREATE_MONSTER_MESSAGE = "successfulCreateMonster";
    private static final String SUCCESSFUL_UPDATE_MONSTER_MESSAGE = "successfulUpdateMonster";
    private static final String INVALID_DATA_MESSAGE = "invalidData";

    public CommandResult goToMonstersPage(RequestContent content){
        CommandResult commandResult;
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

    public CommandResult pickMonster(Content content){
        CommandResult commandResult;
        Monster currentMonster;
        List<Comment> comments;
        String monsterId = content.getRequestParameters().get(MONSTERS_ID_PARAMETER)[0];
        if (MonsterValidation.showMonsterValidator(monsterId)){
            MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
            CommentDao commentDao = SingletonDaoProvider.INSTANCE.getCommentDao();
            TransactionExecutor transactionExecutor = new TransactionExecutor();
            transactionExecutor.beginTransaction(monsterDao, commentDao);
            currentMonster = (Monster) monsterDao.findById(Integer.parseInt(monsterId));
            comments = commentDao.findAllByMonsterId(Integer.parseInt(monsterId));
            transactionExecutor.commit();
            transactionExecutor.endTransaction();
            content.getSessionAttributes().put(MONSTER_ATTRIBUTE, currentMonster);
            content.getSessionAttributes().put(COMMENTS_ATTRIBUTE, comments);
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(MONSTER_PAGE_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult changeMonsterDescription(RequestContent content){
        CommandResult commandResult;
        String changedMonsterDescription = content.getRequestParameters().get(DESCRIPTION_PARAMETER)[0].trim();
        if (MonsterValidation.changeMonsterDescriptionValidator(changedMonsterDescription)){
            Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (new UserValidation().hasRoleAdminOrUser(account)){
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
                        MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult goToAddMonsterPage(RequestContent content){
        CommandResult commandResult;
        Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
        if (UserValidation.hasRoleAdmin(account)){
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                PageManager.getProperty(ADD_MONSTER_PROPERTY));
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult addMonster(RequestContent content){
        CommandResult commandResult;
        String name = content.getRequestParameters().get(NAME_PARAMETER)[0];
        String raceName = content.getRequestParameters().get(RACE_PARAMETER)[0];
        String description = content.getRequestParameters().get(DESCRIPTION_PARAMETER)[0];
        if (MonsterValidation.addMonsterValidator(name, raceName, description)){
            Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (UserValidation.hasRoleAdmin(account)){
                Race race;
                String picturePath = (String) content.getRequestAttributes().get(FILE_PATH_ATTRIBUTE);
                TransactionExecutor transactionExecutor = new TransactionExecutor();
                RaceDao raceDao = SingletonDaoProvider.INSTANCE.getRaceDao();
                MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
                transactionExecutor.beginTransaction(raceDao, monsterDao);
                race = (Race) raceDao.findByRace(raceName);
                transactionExecutor.commit();
                if (race == null){
                    race = new Race(DEFAULT_ID, raceName);
                    raceDao.create(race);
                    transactionExecutor.commit();
                    race = (Race) raceDao.findByRace(raceName);
                }
                monsterDao.create(new Monster(DEFAULT_ID, name, race, description,
                        DEFAULT_AVERAGE_RATING, picturePath));
                transactionExecutor.commit();
                transactionExecutor.endTransaction();
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.getMessage(SUCCESSFUL_CREATE_MONSTER_MESSAGE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(MONSTER_PAGE_PROPERTY));
            }else {
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult goToUpdateMonsterPage(RequestContent content) {
        CommandResult commandResult;
        String name = content.getRequestParameters().get(NAME_PARAMETER)[0];
        if (MonsterValidation.showUpdateMonsterPageValidator(name)){
            Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (UserValidation.hasRoleAdmin(account)){
                MonsterDao monsterDao = new MonsterDao();
                TransactionExecutor transactionExecutor = new TransactionExecutor();
                transactionExecutor.beginTransaction(monsterDao);
                Monster currentMonster = (Monster) monsterDao.findByName(name);
                content.getSessionAttributes().put(MONSTER_ATTRIBUTE, currentMonster);
                transactionExecutor.commit();
                transactionExecutor.endTransaction();
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(UPDATE_MONSTER_PROPERTY));
            }else {
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }

    public CommandResult updateMonster(RequestContent content){
        CommandResult commandResult;
        String updatedMonsterId = content.getRequestParameters().get(MONSTERS_ID_PARAMETER)[0];
        String name = content.getRequestParameters().get(NAME_PARAMETER)[0];
        String raceName = content.getRequestParameters().get(RACE_PARAMETER)[0];
        String description = content.getRequestParameters().get(DESCRIPTION_PARAMETER)[0];
        if (MonsterValidation.updateMonsterValidator(updatedMonsterId, name, raceName, description)){
            Object account = content.getSessionAttributes().get(USER_ATTRIBUTE);
            if (UserValidation.hasRoleAdmin(account)){
                String picturePath = (String) content.getRequestAttributes().get(FILE_PATH_ATTRIBUTE);
                TransactionExecutor transactionExecutor = new TransactionExecutor();
                RaceDao raceDao = SingletonDaoProvider.INSTANCE.getRaceDao();
                MonsterDao monsterDao = SingletonDaoProvider.INSTANCE.getMonsterDao();
                transactionExecutor.beginTransaction(raceDao, monsterDao);
                Race race = (Race) raceDao.findByRace(raceName);
                transactionExecutor.commit();
                if (race == null){
                    race = new Race(DEFAULT_ID, raceName);
                    raceDao.create(race);
                    transactionExecutor.commit();
                    race = (Race) raceDao.findByRace(raceName);
                }
                monsterDao.update(new Monster(Integer.parseInt(updatedMonsterId),
                        name, race, description, DEFAULT_AVERAGE_RATING, picturePath));
                transactionExecutor.commit();
                transactionExecutor.endTransaction();
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.getMessage(SUCCESSFUL_UPDATE_MONSTER_MESSAGE));
                commandResult = goToMonstersPage(content);
            }else {
                content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                        MessageManager.getMessage(NOT_ENOUGH_RIGHTS_ATTRIBUTE));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                        PageManager.getProperty(ERROR_PAGE_PROPERTY));
            }
        }else {
            content.getSessionAttributes().put(RESULT_ATTRIBUTE,
                    MessageManager.getMessage(INVALID_DATA_MESSAGE));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD,
                    PageManager.getProperty(ERROR_PAGE_PROPERTY));
        }
        return commandResult;
    }
}
