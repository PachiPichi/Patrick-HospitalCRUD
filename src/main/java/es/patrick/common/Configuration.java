package es.patrick.common;

import jakarta.ejb.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class Configuration {
    private final Properties p;

    public Configuration() {
        p = new Properties();
        try {
            InputStream propertiesStream =
                    getClass().getClassLoader().getResourceAsStream("MYSQL_PROPERTIES");
            p.loadFromXML(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}
