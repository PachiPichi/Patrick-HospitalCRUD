package es.patrick.common;

import jakarta.ejb.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                    getClass().getClassLoader().getResourceAsStream(Constantes.MYSQL_PROPERTIES);
            if (propertiesStream == null) {
                throw new IOException("No se encontró el archivo de propiedades " + Constantes.MYSQL_PROPERTIES);
            }
            p.loadFromXML(propertiesStream);
        } catch (IOException e) {
            log.error("Error cargando fichero de propiedades: {}", e.getMessage());
            throw new IllegalStateException("No se pudo cargar la configuración de la base de datos", e);
        }
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}
