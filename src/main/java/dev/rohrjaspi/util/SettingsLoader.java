package dev.rohrjaspi.util;

import java.io.InputStream;
import java.util.Properties;

public class SettingsLoader {

    private static Properties properties = new Properties();

    public SettingsLoader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("settings.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getString(String key) {
        return properties.getProperty(key);
    }



}
