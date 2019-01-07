package com.senchenko.aliens.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

public enum ConnectionPool{
    INSTANCE;
    private static final String DATABASE_PROPERTY = "property.database";
    private final Logger LOGGER = LogManager.getLogger();
    private ResourceBundle resource = ResourceBundle.getBundle(DATABASE_PROPERTY);
    private final int POOL_SIZE = Integer.parseInt(resource.getString("poolSize"));
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;

    ConnectionPool(){
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection = null;
            try {
                connection = new ProxyConnection(ConnectorDb.getConnection());
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Connection pool initialization failed ", e);
            }
            connectionQueue.offer(connection);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            try {
                connectionQueue.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Failed to return connection ", e);
                Thread.currentThread().interrupt();
            }
        } else {
                LOGGER.log(Level.ERROR, "Failed to return connection ");
        }
    }

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Error getting connection ", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }
    public void closeConnection(ProxyConnection connection) {
        if (connectionQueue.offer(connection)){
            LOGGER.log(Level.ERROR, "Connection successfully returned: ", connection.toString());
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ProxyConnection connection = connectionQueue.take();
                connection.closeConnection();
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Error getting connection ", e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Error destroy connection ", e);
            }
        }
    }
}