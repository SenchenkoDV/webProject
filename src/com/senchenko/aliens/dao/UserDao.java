package com.senchenko.aliens.dao;

import com.senchenko.aliens.configuration.ConnectionPool;
import com.senchenko.aliens.entity.*;
import com.senchenko.aliens.util.SqlQueries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AliensDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id_user"),
                        new Role(
                                resultSet.getInt("id_role"),
                                resultSet.getString("role")),
                        resultSet.getInt("rating"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return users;
    }

    @Override
    public Entity findById(int id) {
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_USER_BY_ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id_user"),
                        new Role(
                                resultSet.getInt("id_role"),
                                resultSet.getString("role")),
                        resultSet.getInt("rating"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return user;
    }
    public Entity findUserByLogin(String login) {
        User user = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id_user"),
                        new Role(
                                resultSet.getInt("id_role"),
                                resultSet.getString("role")),
                        resultSet.getInt("rating"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return user;
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_DELETE_USER_BY_ID);
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
        User user = (User) entity;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_INSERT_USER);
            statement.setInt(1, user.getRole().getRoleId());
            statement.setInt(2, user.getRating());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
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
        User user = (User) entity;
        try {
            statement = connection.prepareStatement(SqlQueries.SQL_UPDATE_USER);
            statement.setInt(1, user.getRole().getRoleId());
            statement.setInt(2, user.getRating());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return user;
    }
}
