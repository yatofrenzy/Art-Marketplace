package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.CartItem;
import com.artmarketplace.utilities.DbConfig;

public class CartDAO {

    public boolean addToCart(int userId, int artworkId) {
        String checkSql = "SELECT quantity FROM cart_items WHERE user_id=? AND artwork_id=?";
        String updateSql = "UPDATE cart_items SET quantity = quantity + 1 WHERE user_id=? AND artwork_id=?";
        String insertSql = "INSERT INTO cart_items (user_id, artwork_id, quantity) VALUES (?, ?, 1)";

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

        String sql = "SELECT c.cart_item_id, c.artwork_id, c.quantity, " +
                     "a.title, a.price, a.image_path " +
                     "FROM cart_items c " +
                     "JOIN artworks a ON c.artwork_id = a.artwork_id " +
                     "WHERE c.user_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setArtworkId(rs.getInt("artwork_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setTitle(rs.getString("title"));
                item.setPrice(rs.getDouble("price"));
                item.setImagePath(rs.getString("image_path"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public boolean updateQuantity(int cartItemId, int quantity) {
        String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}