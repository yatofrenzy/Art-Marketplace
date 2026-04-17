package com.artmarketplace.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDb {

    // 1. Define your database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/art_marketplace";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        System.out.println("Initiating database connection test...");
        
        // Use try-with-resources to automatically close the connection after execution
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            
            // Check if the connection object is successfully established
            if (connection != null) {
                System.out.println("🟢 SUCCESS: Connected to the database!");

                // Check if the open connection is actively valid/alive (3-second timeout)
                boolean isValid = connection.isValid(3);
                System.out.println("📡 Connection validity status: " + (isValid ? "Active" : "Inactive"));
            }
            
        } catch (SQLException e) {
            System.err.println("🔴 FAILURE: Could not connect to the database.");
            System.err.println("Reason: " + e.getMessage());
        }
    }
}
