package com.senchenko.aliens.manager;

import java.util.ResourceBundle;

public class PageManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("property.command");

    public static String getProperty(String key) {
        String resource;
        resource = resourceBundle.getString(key);
        return resource;
    }
}
