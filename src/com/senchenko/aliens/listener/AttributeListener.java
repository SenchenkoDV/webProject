package com.senchenko.aliens.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class AttributeListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    private static final Logger LOGGER = LogManager.getLogger();

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      LOGGER.log(Level.INFO, "Added: " + sbe.getClass().getSimpleName()
              + " : " + sbe.getName() + " : " + sbe.getValue());
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        LOGGER.log(Level.INFO, "Removed: " + sbe.getClass().getSimpleName()
                + " : " + sbe.getName() + " : " + sbe.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        LOGGER.log(Level.INFO, "Replaced: " + sbe.getClass().getSimpleName()
                + " : " + sbe.getName() + " : " + sbe.getValue());
    }
}
