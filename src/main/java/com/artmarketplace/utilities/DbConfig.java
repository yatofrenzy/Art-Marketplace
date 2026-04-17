package com.artmarketplace.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/art_marketplace_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}