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
    private static final String DATABASE_PROPERTY = "property.database";
    private static final String DATABASE_URL = "url";
    private static final String DATABASE_USER = "user";
    private static final String DATABASE_PASS = "password";

    private ConnectorDb() {
        throw new IllegalStateException("Utility class");
    }
    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_PROPERTY);
        String url = resource.getString(DATABASE_URL);
        String user = resource.getString(DATABASE_USER);
        String pass = resource.getString(DATABASE_PASS);
        try {
            Class.forName(resource.getString("driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "SQL connector exception ", e);
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
