package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.artmarketplace.model.User;
import com.artmarketplace.utilities.DbConfig;

public class UserDAO {

    public boolean registerUser(User user) {
        boolean isRegistered = false;
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("UserDAO.registerUser called");
            System.out.println("user name = " + user.getName());
            System.out.println("user email = " + user.getEmail());
            System.out.println("user password = " + user.getPassword());
            System.out.println("user role = " + user.getRole());

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            int rows = ps.executeUpdate();
            System.out.println("rows inserted = " + rows);

            if (rows > 0) {
                isRegistered = true;
            }

        } catch (Exception e) {
            System.out.println("registerUser error happened");
            e.printStackTrace();
        }

        return isRegistered;
    }

    public boolean isEmailExists(String email) {
        boolean exists = false;
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

            System.out.println("isEmailExists checked: " + email + " -> " + exists);

        } catch (Exception e) {
            System.out.println("isEmailExists error happened");
            e.printStackTrace();
        }

        return exists;
    }
}