package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.CartDAOInterface;
import com.artmarketplace.utilities.DBConnection;

import java.sql.*;

public class CartDAO implements CartDAOInterface {

    public boolean addToCart(int userId, int artworkId, int quantity) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO cart(user_id, artwork_id, quantity) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, artworkId);
            ps.setInt(3, quantity);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}