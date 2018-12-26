package com.senchenko.aliens.dao;

import com.senchenko.aliens.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AliensDao<T extends Entity> implements CrudDao {
    private static final Logger LOGGER = LogManager.getLogger();
    Connection connection;

    public abstract List<T> findAll();
    public abstract Entity findById(int id);
    public abstract boolean deleteById(int id);
    public abstract boolean delete(Entity entity);
    public abstract boolean create(Entity entity);
    public abstract Entity update(Entity entity);

    void closeStatement(Statement statement) {
        try {
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Error closing statement", e);
        }
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
