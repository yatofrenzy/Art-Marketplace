package com.artmarketplace.dao;

import com.artmarketplace.utilities.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Data Access Object (DAO) responsible for managing database records for individual ordered items.
 * Part of the DAO layer in the MVC architecture.
 * Handles insertion queries for order items details into the database.
 */
public class OrderItemDAO {

    /**
     * Inserts an itemized record line linked to an order.
     * Note: The orderId parameter is defined as boolean in the signature (legacy logic).
     * 
     * @param orderId   The boolean representation/flag for the parent order.
     * @param artworkId The unique identifier of the artwork purchased.
     * @param quantity  The quantity units ordered.
     * @param price     The purchase price rate.
     * @return {@code true} if inserted successfully; {@code false} otherwise.
     */
    public boolean addOrderItem(boolean orderId, int artworkId, int quantity, double price) {
        boolean status = false;

        // Try-with-resources: connects to the database and closes resources when finished
        try (Connection conn = DbConfig.getConnection()) {

            // SQL insert query template for inserting single order item details
            String sql = "INSERT INTO order_item(order_id, artwork_id, quantity, price) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            // Bind the input parameter variables to the query placeholders
            ps.setBoolean(1, orderId);
            ps.setInt(2, artworkId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            // Execute insert query and verify if any database rows were changed
            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Print execution errors to help with debugging
            e.printStackTrace();
        }

        return status;
    }
}