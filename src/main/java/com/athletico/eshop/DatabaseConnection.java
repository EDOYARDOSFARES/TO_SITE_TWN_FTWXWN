package com.athletico.eshop;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Reads database credentials from config.properties (in the project root)
 * and opens a JDBC connection to the MySQL database.
 */
public class DatabaseConnection {

    private static final String CONFIG_FILE = "config.properties";

    public static Connection getConnection() throws SQLException {
        Properties props = loadConfig();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    private static Properties loadConfig() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read " + CONFIG_FILE + ". Make sure it exists in the project root "
                            + "(same folder as pom.xml) with db.url, db.user, and db.password set.",
                    e
            );
        }
        return props;
    }

    // Quick standalone test - run this file directly to confirm the connection works,
    // without needing to launch the whole JavaFX app.
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connected successfully to: " + conn.getCatalog());
        } catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
    }
}