package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.CartItem;
import com.artmarketplace.model.Order;
import com.artmarketplace.model.OrderItem;
import com.artmarketplace.utilities.DbConfig;

/**
 * Data Access Object (DAO) responsible for managing customer checkout orders and itemized lines.
 * Part of the DAO layer in the MVC architecture.
 * Manages database transactions for ordering processes, matching cart items to orders, and updates.
 */
public class OrderDAO {

    /**
     * Places a customer order from their current shopping cart items.
     * Implements transaction management using `setAutoCommit(false)` to ensure order details
     * and ordered items are all successfully written, or rolled back as a single atomic unit.
     * 
     * @param userId        The unique ID of the user placing the order.
     * @param paymentMethod The selected payment method string.
     * @return {@code true} if the order transaction is completed successfully; {@code false} otherwise.
     */
    public boolean createOrder(int userId, String paymentMethod) {
        CartDAO cartDAO = new CartDAO();
        // Fetch current active shopping cart items for the user
        List<CartItem> cartItems = cartDAO.getCartItems(userId);

        // Validation check: Cannot checkout with empty cart
        if (cartItems == null || cartItems.isEmpty()) {
            return false;
        }

        // Calculate total amount of the order by summing cart sub-totals
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotal();
        }

        // SQL queries for inserting order summary and itemized checkout records
        String orderSql = "INSERT INTO orders (user_id, total_amount, order_status, payment_method, payment_status) VALUES (?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, artwork_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection()) {

            // Turn off autocommit to initiate transaction block
            conn.setAutoCommit(false);

            // Execute order insertion and request the database to return the generated auto-increment keys
            PreparedStatement orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, userId);
            orderPs.setDouble(2, totalAmount);
            orderPs.setString(3, "Pending");
            orderPs.setString(4, paymentMethod);
            orderPs.setString(5, "Paid");

            int orderRows = orderPs.executeUpdate();

            // Check if order was created, rollback transaction if it failed
            if (orderRows <= 0) {
                conn.rollback();
                return false;
            }

            // Retrieve the newly created order ID generated key
            ResultSet keys = orderPs.getGeneratedKeys();
            int orderId = 0;

            if (keys.next()) {
                orderId = keys.getInt(1);
            }

            PreparedStatement itemPs = conn.prepareStatement(itemSql);

            // Add batch elements of order items to prevent multiple network roundtrips
            for (CartItem item : cartItems) {
                itemPs.setInt(1, orderId);
                itemPs.setInt(2, item.getArtworkId());
                itemPs.setInt(3, item.getQuantity());
                itemPs.setDouble(4, item.getPrice());
                itemPs.addBatch();
            }

            // Execute the bulk items insertion
            itemPs.executeBatch();

            // SQL statement to clean up/delete shopping cart items after successful checkout conversion
            String clearSql = "DELETE FROM cart_items WHERE user_id=?";
            PreparedStatement clearPs = conn.prepareStatement(clearSql);
            clearPs.setInt(1, userId);
            clearPs.executeUpdate();

            // Commit transaction changes to DB
            conn.commit();
            return true;

        } catch (Exception e) {
            // Log exceptions and stack trace
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all checkout orders placed by a specific user.
     * 
     * @param userId The unique user identifier.
     * @return A {@link List} of {@link Order}s, sorted by order ID descending.
     */
    public List<Order> getOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();

        // SQL query to filter orders by user ID
        String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY order_id DESC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind filter parameter
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            // Map each row to Order model object
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setOrderDate(rs.getString("order_date"));
                // Recursively fetch sub-items belonging to this order
                order.setItems(getOrderItems(order.getOrderId()));

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Retrieves all checkout orders in the system, including customer name details.
     * Used exclusively in administration panels.
     * 
     * @return A {@link List} of all {@link Order} records in descending chronological order.
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        // Join query to obtain customer name alongside order details
        String sql = "SELECT o.*, u.name AS customer_name " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.user_id " +
                     "ORDER BY o.order_id DESC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setOrderDate(rs.getString("order_date"));
                order.setCustomerName(rs.getString("customer_name"));
                // Recursively fetch sub-items belonging to this order
                order.setItems(getOrderItems(order.getOrderId()));

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Updates the status of an existing order.
     * 
     * @param orderId The unique identifier of the order.
     * @param status  The new status tag to assign (e.g., Shipped).
     * @return {@code true} if order status updated; {@code false} otherwise.
     */
    public boolean updateOrderStatus(int orderId, String status) {
        // SQL query statement
        String sql = "UPDATE orders SET order_status=? WHERE order_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind values
            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves all item lines purchased within a specific order.
     * Joins order_items, artworks, and categories tables.
     * 
     * @param orderId The parent order identifier.
     * @return A list of {@link OrderItem}s.
     */
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();

        // SQL join query to retrieve ordered item details
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.artwork_id, oi.quantity, oi.price, " +
                     "a.title, c.category_name " +
                     "FROM order_items oi " +
                     "JOIN artworks a ON oi.artwork_id = a.artwork_id " +
                     "JOIN categories c ON a.category_id = c.category_id " +
                     "WHERE oi.order_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind target order ID filter parameter
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setArtworkId(rs.getInt("artwork_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setTitle(rs.getString("title"));
                item.setCategoryName(rs.getString("category_name"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}