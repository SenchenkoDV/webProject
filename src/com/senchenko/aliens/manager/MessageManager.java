package com.senchenko.aliens.manager;

import java.util.ResourceBundle;

public class MessageManager {
    private static final String MESSAGES_PROPERTY = "property.messages";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(MESSAGES_PROPERTY);

    public static String getMessage(String key) {
        String resource;
        resource = resourceBundle.getString(key);
        return resource;
    }
}
