package es.patrick.common;

import jakarta.ejb.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
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
            log.error("Error cargando fichero de propiedades: {}", e.getMessage());
        }
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}
