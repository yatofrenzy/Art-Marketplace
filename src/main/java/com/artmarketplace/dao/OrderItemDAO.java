package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.OrderItemDAOInterface;
import com.artmarketplace.utilities.DBConnection;

import java.sql.*;

public class OrderItemDAO implements OrderItemDAOInterface {

    public boolean addOrderItem(int orderId, int artworkId, int quantity, double price) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO order_item(order_id, artwork_id, quantity, price) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
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