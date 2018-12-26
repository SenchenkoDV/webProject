package com.senchenko.aliens.dao;

import com.senchenko.aliens.configuration.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {
    private static final Logger LOGGER = LogManager.getLogger();
    private Connection connection = ConnectionPool.INSTANCE.getConnection();
    public void beginTransaction(AliensDao dao, AliensDao... daos){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Begin SQL transaction exception ", e);
        }
        dao.setConnection(connection);
        for (AliensDao abstractAliensDao : daos){
            abstractAliensDao.setConnection(connection);
        }
    }
    public void endTransaction(){
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            LOGGER.log(Level.ERROR, "End SQL transaction exception ", e);
        }
        ConnectionPool.INSTANCE.releaseConnection(connection);
    }
    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            rollback();
            LOGGER.log(Level.ERROR, "Commit exception ", e);
        }
    }
    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Rollback exception ", e);
        }
    }
}
