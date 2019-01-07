package com.senchenko.aliens.dao;

import com.senchenko.aliens.entity.Entity;
import com.senchenko.aliens.entity.Race;
import com.senchenko.aliens.util.SqlQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RaceDao extends AliensDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Race> findAll() {
        List<Race> races = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_ALL_RACES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Race race = new Race(
                        resultSet.getInt(SqlQueries.ID_RACE),
                        resultSet.getString(SqlQueries.RACE)
                );
                races.add(race);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return races;
    }

    @Override
    public Entity findById(int id) {
        Race race = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_RACE_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                race = new  Race(
                        resultSet.getInt(SqlQueries.ID_RACE),
                        resultSet.getString(SqlQueries.RACE)
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return race;
    }

    public Entity findByRace(String raceName) {
        Race race = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_RACE_BY_RACE);
            statement.setString(1, raceName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                race = new  Race(
                        resultSet.getInt(SqlQueries.ID_RACE),
                        resultSet.getString(SqlQueries.RACE)
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return race;
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_DELETE_RACE_BY_ID);
            statement.setInt(1, id);
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
    public boolean delete(Entity entity) {
        int id = entity.getId();
        return deleteById(id);
    }

    @Override
    public boolean create(Entity entity) {
        boolean flag = false;
        PreparedStatement statement = null;
        Race race = (Race) entity;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_INSERT_RACE);
            statement.setString(1, race.getRace());
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
    public Entity update(Entity entity) {
        PreparedStatement statement = null;
        Race race = (Race) entity;
        try {
            statement.setString(1, race.getRace());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception " + e);
        } finally {
            this.closeStatement(statement);
        }
        return race;
    }
}
