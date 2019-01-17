package com.senchenko.aliens.dao;

import com.senchenko.aliens.configuration.ConnectionPool;
import com.senchenko.aliens.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonsterDao extends AliensDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Monster> findAll() {
        List<Monster> monsters = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_ALL_MONSTERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Monster monster = new Monster(
                        resultSet.getInt(SqlQueries.ID_MONSTER),
                        resultSet.getString(SqlQueries.NAME),
                        new Race(
                                resultSet.getInt(SqlQueries.ID_RACE),
                                resultSet.getString(SqlQueries.RACE)),
                        resultSet.getString(SqlQueries.DESCRIPTION),
                        resultSet.getDouble(SqlQueries.AVERAGE_RATING),
                        resultSet.getString(SqlQueries.PICTURE_ADDRESS)
                );
                monsters.add(monster);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception " + e);
        } finally {
            this.closeStatement(statement);
        }
        return monsters;
    }

    @Override
    public Entity findById(int id) {
        Monster monster = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_MONSTER_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                monster = new Monster(
                        resultSet.getInt(SqlQueries.ID_MONSTER),
                        resultSet.getString(SqlQueries.NAME),
                        new Race(
                                resultSet.getInt(SqlQueries.ID_RACE),
                                resultSet.getString(SqlQueries.RACE)),
                        resultSet.getString(SqlQueries.DESCRIPTION),
                        resultSet.getDouble(SqlQueries.AVERAGE_RATING),
                        resultSet.getString(SqlQueries.PICTURE_ADDRESS)
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception " + e);
        } finally {
            this.closeStatement(statement);
        }
        return monster;
    }

    public Entity findByName(String name) {
        Monster monster = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_MONSTER_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                monster = new Monster(
                        resultSet.getInt(SqlQueries.ID_MONSTER),
                        resultSet.getString(SqlQueries.NAME),
                        new Race(
                                resultSet.getInt(SqlQueries.ID_RACE),
                                resultSet.getString(SqlQueries.RACE)),
                        resultSet.getString(SqlQueries.DESCRIPTION),
                        resultSet.getDouble(SqlQueries.AVERAGE_RATING),
                        resultSet.getString(SqlQueries.PICTURE_ADDRESS)
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception " + e);
        } finally {
            this.closeStatement(statement);
        }
        return monster;
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_DELETE_MONSTER_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception " + e);
        } finally {
            this.closeStatement(statement);
        }
        return flag;
    }

    @Override
    public boolean delete(Entity entity) {
        int id = entity.getId();
        return deleteById(id);
    }

    @Override
    public boolean create(Entity entity) {
        boolean flag = false;
        PreparedStatement statement = null;
        Monster monster = (Monster) entity;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_INSERT_MONSTER);
            statement.setString(1, monster.getName());
            statement.setInt(2, monster.getRace().getRaceId());
            statement.setString(3, monster.getDescription());
            statement.setDouble(4, monster.getAverageRating());
            statement.setString(5, monster.getPictureAddress());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return flag;
    }

    @Override
    public Entity update(Entity entity) {
        PreparedStatement statement = null;
        Monster monster = (Monster) entity;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_MONSTER);
            statement.setString(1, monster.getName());
            statement.setInt(2, monster.getRace().getRaceId());
            statement.setString(3, monster.getDescription());
            statement.setDouble(4, monster.getAverageRating());
            statement.setString(5, monster.getPictureAddress());
            statement.setInt(6, monster.getMonsterId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return monster;
    }
}
