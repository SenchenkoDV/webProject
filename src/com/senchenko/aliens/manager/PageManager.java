package com.senchenko.aliens.manager;

        import com.senchenko.aliens.exception.AliensException;

        import java.util.MissingResourceException;
        import java.util.ResourceBundle;

public class PageManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("property.command");
    public static String getProperty(String key){
        //todo
        String resource;
//        try {
            resource = resourceBundle.getString(key);
//        }catch (MissingResourceException e){
//            throw new AliensException("Missing resource by key: " + key, e);
//        }
        return resource;
    }
}
