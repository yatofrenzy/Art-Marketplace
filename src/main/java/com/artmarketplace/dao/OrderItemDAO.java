package com.artmarketplace.dao;

import com.artmarketplace.utilities.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderItemDAO {

    public boolean addOrderItem(boolean orderId, int artworkId, int quantity, double price) {
        boolean status = false;

        try (Connection conn = DbConfig.getConnection()) {

            String sql = "INSERT INTO order_item(order_id, artwork_id, quantity, price) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, orderId);
            ps.setInt(2, artworkId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}