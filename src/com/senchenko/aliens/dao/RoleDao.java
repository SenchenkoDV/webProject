package com.senchenko.aliens.dao;

import com.senchenko.aliens.configuration.ConnectionPool;
import com.senchenko.aliens.entity.Entity;
import com.senchenko.aliens.entity.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao extends AliensDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(SqlQueries.ID_ROLE),
                        resultSet.getString(SqlQueries.ROLE)
                );
                roles.add(role);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return roles;
    }

    @Override
    public Entity findById(int id) {
        Role role = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                role = new Role(
                        resultSet.getInt(SqlQueries.ID_ROLE),
                        resultSet.getString(SqlQueries.ROLE)
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return role;
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
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
        Role role = (Role) entity;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement.setString(1, role.getRole());
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
        Role role = (Role) entity;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement.setString(1, role.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQL exception ", e);
        } finally {
            this.closeStatement(statement);
        }
        return role;
    }
}
