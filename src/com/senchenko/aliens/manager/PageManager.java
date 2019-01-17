package com.senchenko.aliens.manager;

import java.util.ResourceBundle;

public class PageManager {
    private static final String COMMAND_PROPERTY = "property.command";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(COMMAND_PROPERTY);

    public static String getProperty(String key) {
        String resource;
        resource = resourceBundle.getString(key);
        return resource;
    }

    String object1= new String();
    Object object2= new Object();
}
