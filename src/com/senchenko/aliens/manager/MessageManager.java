package com.senchenko.aliens.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public enum  MessageManager {
    EN(ResourceBundle.getBundle("property.messages", new Locale("en", "EN"))),
    RU(ResourceBundle.getBundle("property.messages", new Locale("ru", "RU")));
    private ResourceBundle bundle;

    MessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
