package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.User;
import com.artmarketplace.utilities.DbConfig;

/**
 * Data Access Object (DAO) class for managing user accounts in the system.
 * Handles database operations including user registration, authentication, 
 * profile updates, password resets, and admin status management.
 * Part of the DAO layer in the MVC architecture.
 * 
 * @author Your Name
 * @version 1.0
 */
public class UserDAO {

    /**
     * Registers a new user into the database with default statuses.
     * 
     * @param user The {@link User} object containing registration details.
     * @return {@code true} if the user was successfully registered; {@code false} otherwise.
     */
    public boolean registerUser(User user) {
        // SQL query to insert new user record
        String sql = "INSERT INTO users (name, email, password, role, phone, account_status) VALUES (?, ?, ?, ?, ?, ?)";

        // Try-with-resources: Auto-closes connection and prepared statement
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind values to placeholders
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAccountStatus());

            // Execute insert statement
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Checks if an email address is already registered in the system.
     * Used to prevent duplicate accounts during registration.
     * 
     * @param email The email address to look up.
     * @return {@code true} if the email exists in the users table; {@code false} otherwise.
     */
    public boolean isEmailExists(String email) {
        // SQL query to check if email exists in database
        String sql = "SELECT user_id FROM users WHERE email = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind the email parameter
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            // Return true if ResultSet contains a matching record row
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all customer profiles alongside their total checkout order counts.
     * Performs a LEFT JOIN between the users and orders tables.
     * 
     * @return A {@link List} of {@link User} profiles with order counts populated.
     */
    public java.util.List<User> getAllCustomersWithOrderCount() {
        java.util.List<User> customers = new java.util.ArrayList<>();

        // SQL LEFT JOIN query to aggregate customer users and order quantities
        String sql = "SELECT u.user_id, u.name, u.email, u.phone, u.account_status, " +
                     "COUNT(o.order_id) AS order_count " +
                     "FROM users u " +
                     "LEFT JOIN orders o ON u.user_id = o.user_id " +
                     "WHERE u.role = 'customer' " +
                     "GROUP BY u.user_id, u.name, u.email, u.phone, u.account_status " +
                     "ORDER BY u.user_id DESC";

        // Try-with-resources to automatically close the DB connection, statement, and result set
        try (java.sql.Connection conn = DbConfig.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            // Iterate over retrieved records to construct customer models
            while (rs.next()) {
                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAccountStatus(rs.getString("account_status"));
                user.setOrderCount(rs.getInt("order_count"));

                customers.add(user);
            }

        } catch (Exception e) {
            // Logs any database execution or mapping error
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Retrieves a complete user profile by their email address.
     * This method handles fallback mappings gracefully for optional 
     * legacy database fields like contact_number and profile_image.
     * 
     * @param email The email address of the target user.
     * @return A {@link User} object filled with database records, or {@code null} if no user is found.
     */
    public User getUserByEmail(String email) {
        // SQL query to retrieve a user record matching the email
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind parameter
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
                user.setAccountStatus(rs.getString("account_status"));

                // Fallback exception handling for contact_number (if column is missing/different in schema)
                try {
                    user.setContactNumber(rs.getString("contact_number"));
                } catch (Exception ignored) {}

                // Fallback exception handling for profile_image (if column is missing/different in schema)
                try {
                    user.setProfileImage(rs.getString("profile_image"));
                } catch (Exception ignored) {}

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates editable profile parameters for an existing user.
     * Updates email address, optional contact number, and profile image file path string.
     * 
     * @param user The {@link User} object housing updated properties linked to an existing ID.
     * @return {@code true} if the record row was updated successfully; {@code false} otherwise.
     */
    public boolean updateProfile(User user) {
        // SQL query to modify user profile information
        String sql = "UPDATE users SET email=?, contact_number=?, profile_image=? WHERE user_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind parameters
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getContactNumber());
            ps.setString(3, user.getProfileImage());
            ps.setInt(4, user.getUserId());

            // Execute the update
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Fetches basic user records matching both email address and primary phone.
     * Commonly utilized for account recovery verifications.
     * 
     * @param email The user's registered email address.
     * @param phone The user's registered phone number string.
     * @return A restricted details {@link User} entity object, or {@code null} if the lookup blocks fail.
     */
    public User getUserByEmailAndPhone(String email, String phone) {
        // SQL query to verify email and phone
        String sql = "SELECT * FROM users WHERE email=? AND phone=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind values
            ps.setString(1, email);
            ps.setString(2, phone);

            ResultSet rs = ps.executeQuery();

            // Populate restricted fields for identity validation check
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAccountStatus(rs.getString("account_status"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Modifies the security password string for an account row entry.
     * 
     * @param userId The unique integer identifier primary key of the target user account.
     * @param newPassword The plain text or hashed string value of the replacement password.
     * @return {@code true} if update is successful; {@code false} otherwise.
     */
    public boolean updatePassword(int userId, String newPassword) {
        // SQL query to update user password
        String sql = "UPDATE users SET password=? WHERE user_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind input values
            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            // Execute database update
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Pulls a list collection tracking all customer records containing a "Pending" status flag.
     * Sorted dynamically in reverse order starting from the newest accounts.
     * 
     * @return A {@link List} containing {@link User} profiles matching criteria, or empty if none match.
     */
    public List<User> getPendingCustomers() {
        List<User> users = new ArrayList<>();

        // SQL query to fetch customer accounts with status 'Pending'
        String sql = "SELECT * FROM users WHERE role='customer' AND account_status='Pending' ORDER BY user_id DESC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Loop through results and build User objects list
            while (rs.next()) {
                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
                user.setAccountStatus(rs.getString("account_status"));

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Changes administrative processing markers specifically matching customer accounts.
     * Updates flag states such as changing values to 'Approved', 'Rejected', or 'Suspended'.
     * 
     * @param userId The target customer identifier primary key integer.
     * @param status The target state update string configuration value.
     * @return {@code true} if state changed successfully; {@code false} if targeted row is not matching customer role.
     */
    public boolean updateAccountStatus(int userId, String status) {
        // SQL query to modify account_status for customer role only
        String sql = "UPDATE users SET account_status=? WHERE user_id=? AND role='customer'";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind new status and user ID parameters
            ps.setString(1, status);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
