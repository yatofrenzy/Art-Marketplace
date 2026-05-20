package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.CartItem;
import com.artmarketplace.utilities.DbConfig;

/**
 * Data Access Object (DAO) responsible for managing shopping cart operations in the database.
 * Part of the DAO layer in the MVC architecture.
 * Implements operations on the cart_items database table, such as adding, retrieving, updating quantities, and clearing.
 */
public class CartDAO {

    /**
     * Adds an artwork to the user's shopping cart.
     * If the item is already present, increases its quantity by 1.
     * Otherwise, inserts a new cart item entry with quantity 1.
     * 
     * @param userId    The unique ID of the user.
     * @param artworkId The unique ID of the artwork.
     * @return {@code true} if cart was updated successfully; {@code false} otherwise.
     */
    public boolean addToCart(int userId, int artworkId) {
        // Query to check if the artwork already exists in the user's cart
        String checkSql = "SELECT quantity FROM cart_items WHERE user_id=? AND artwork_id=?";
        // SQL query to increment the quantity of an existing item in the cart
        String updateSql = "UPDATE cart_items SET quantity = quantity + 1 WHERE user_id=? AND artwork_id=?";
        // SQL query to insert a new artwork record into the cart with quantity 1
        String insertSql = "INSERT INTO cart_items (user_id, artwork_id, quantity) VALUES (?, ?, 1)";

        try (Connection conn = DbConfig.getConnection()) {

            // Create PreparedStatement to check for existing items in cart
            PreparedStatement check = conn.prepareStatement(checkSql);
            check.setInt(1, userId);
            check.setInt(2, artworkId);

            ResultSet rs = check.executeQuery();

            // Condition: If item exists, run UPDATE query; otherwise run INSERT query
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
            // Logs error details when database connectivity or syntax exception occurs
            System.out.println("ADD TO CART ERROR:");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all shopping cart items for a specified user.
     * Joins cart_items and artworks tables to fetch detail information like title, price, and image.
     * 
     * @param userId The unique ID of the user.
     * @return A list of {@link CartItem}s in the user's cart.
     */
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> items = new ArrayList<>();

        // SQL join query to retrieve cart items with parent artwork information
        String sql = "SELECT c.cart_item_id, c.artwork_id, c.quantity, " +
                     "a.title, a.price, a.image_path " +
                     "FROM cart_items c " +
                     "JOIN artworks a ON c.artwork_id = a.artwork_id " +
                     "WHERE c.user_id=?";

        try (Connection conn = DbConfig.getConnection();
              PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind user ID filter parameter
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            // Populate the cart item models list by reading database rows sequentially
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
            System.out.println("GET CART ITEMS ERROR:");
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Updates the quantity of a specific cart item entry.
     * 
     * @param cartItemId The unique identifier of the cart item.
     * @param quantity   The new quantity count.
     * @return {@code true} if the update query was successful; {@code false} otherwise.
     */
    public boolean updateQuantity(int cartItemId, int quantity) {
        // SQL query to modify quantity value in the database
        String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind query input parameters
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("UPDATE CART ERROR:");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Removes an item from the shopping cart.
     * 
     * @param cartItemId The unique ID of the cart item to delete.
     * @return {@code true} if deleted successfully; {@code false} otherwise.
     */
    public boolean removeItem(int cartItemId) {
        // SQL query to delete a single cart item record
        String sql = "DELETE FROM cart_items WHERE cart_item_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind target cart item identifier parameter
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("REMOVE CART ERROR:");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes all shopping cart items associated with a user.
     * Usually called after successful order placement checkout.
     * 
     * @param userId The unique ID of the user.
     * @return {@code true} if successfully cleared; {@code false} otherwise.
     */
    public boolean clearCart(int userId) {
        // SQL query to delete all cart items of the user
        String sql = "DELETE FROM cart_items WHERE user_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind user ID query parameter
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("CLEAR CART ERROR:");
            e.printStackTrace();
        }

        return false;
    }
}