package com.artmarketplace.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for establishing database connections.
 * Part of the Utility layer in the MVC architecture.
 * Configures the database connection properties and manages the JDBC driver loading.
 */
public class DbConfig {

    // Database configurations (name, user, password, and URL)
    private static final String DB_NAME = "art_marketplace";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    /**
     * Loads the MySQL JDBC driver and returns a Connection object to the database.
     * 
     * @return A {@link Connection} object connected to the MySQL server database.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class cannot be found.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver class dynamically into memory
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish and return the database connection using the DriverManager
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}