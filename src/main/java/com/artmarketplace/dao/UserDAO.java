package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.artmarketplace.model.User;
import com.artmarketplace.utilities.DbConfig;

public class UserDAO {

    public boolean registerUser(User user) {
        boolean isRegistered = false;
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("=== UserDAO.registerUser called ===");
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getPassword());
            System.out.println("Role: " + user.getRole());

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            int rows = ps.executeUpdate();
            System.out.println("Rows inserted: " + rows);

            if (rows > 0) {
                isRegistered = true;
            }

        } catch (Exception e) {
            System.out.println("=== Register Error ===");
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
            var rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}