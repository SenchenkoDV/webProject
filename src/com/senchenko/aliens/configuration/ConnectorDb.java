package com.senchenko.aliens.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDb {
    private static final Logger LOGGER = LogManager.getLogger();

    private ConnectorDb() {
        throw new IllegalStateException("Utility class");
    }
    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("property/database");
        String url = resource.getString("url");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            Class.forName(resource.getString("driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "SQL connector exception ", e);
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
