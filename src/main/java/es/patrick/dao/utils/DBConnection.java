package es.patrick.dao.utils;

import es.patrick.common.Configuration;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final Logger log = LoggerFactory.getLogger(DBConnection.class);
    private final Configuration config;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(config.getProperty("urlDB"),
                config.getProperty("user_name"), config.getProperty("password"));
        IO.println("Connected to the database");
        log.info("Connected to the database");
        return conn;
    }
}
