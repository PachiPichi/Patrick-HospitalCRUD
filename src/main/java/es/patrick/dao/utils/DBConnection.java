package es.patrick.dao.utils;

import es.patrick.common.Configuration;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());
    private final Configuration config;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(config.getProperty("urlDB"),
                config.getProperty("user_name"), config.getProperty("password"));
        IO.println("Connected to the database");
        logger.log(Level.INFO, "Connected to the database");
        return conn;
    }
}
