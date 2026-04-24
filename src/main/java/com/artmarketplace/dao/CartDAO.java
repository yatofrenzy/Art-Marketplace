package com.artmarketplace.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.CartItem;
import com.artmarketplace.utilities.DbConfig;

public class CartDAO {

   
    public boolean addToCart(int userId, int artworkId) {
        String checkSql = "SELECT quantity FROM cart WHERE user_id=? AND artwork_id=?";
        String updateSql = "UPDATE cart SET quantity = quantity + 1 WHERE user_id=? AND artwork_id=?";
        String insertSql = "INSERT INTO cart (user_id, artwork_id, quantity) VALUES (?, ?, 1)";

        try (Connection conn = DbConfig.getConnection()) {

            PreparedStatement check = conn.prepareStatement(checkSql);
            check.setInt(1, userId);
            check.setInt(2, artworkId);

            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                PreparedStatement update = conn.prepareStatement(updateSql);
                update.setInt(1, userId);
                update.setInt(2, artworkId);
                return update.executeUpdate() > 0;
            } else {
                PreparedStatement insert = conn.prepareStatement(insertSql);
                insert.setInt(1, userId);
                insert.setInt(2, artworkId);
                return insert.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

   
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> items = new ArrayList<>();

        String sql = "SELECT c.cart_id, c.artwork_id, c.quantity, " +
                     "a.title, a.price " +
                     "FROM cart c " +
                     "JOIN artwork a ON c.artwork_id = a.artwork_id " +
                     "WHERE c.user_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_id"));
                item.setArtworkId(rs.getInt("artwork_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setTitle(rs.getString("title"));
                item.setPrice(rs.getDouble("price"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

   
    public boolean updateQuantity(int cartId, int quantity) {
        String sql = "UPDATE cart SET quantity=? WHERE cart_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

   
    public boolean removeItem(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}