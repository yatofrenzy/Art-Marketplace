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

public class OrderDAO {

    public boolean createOrder(int userId, String paymentMethod) {
        CartDAO cartDAO = new CartDAO();
        List<CartItem> cartItems = cartDAO.getCartItems(userId);

        if (cartItems == null || cartItems.isEmpty()) {
            return false;
        }

        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotal();
        }

        String orderSql = "INSERT INTO orders (user_id, total_amount, order_status, payment_method, payment_status) VALUES (?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, artwork_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, userId);
            orderPs.setDouble(2, totalAmount);
            orderPs.setString(3, "Pending");
            orderPs.setString(4, paymentMethod);
            orderPs.setString(5, "Paid");

            int orderRows = orderPs.executeUpdate();

            if (orderRows <= 0) {
                conn.rollback();
                return false;
            }

            ResultSet keys = orderPs.getGeneratedKeys();
            int orderId = 0;

            if (keys.next()) {
                orderId = keys.getInt(1);
            }

            PreparedStatement itemPs = conn.prepareStatement(itemSql);

            for (CartItem item : cartItems) {
                itemPs.setInt(1, orderId);
                itemPs.setInt(2, item.getArtworkId());
                itemPs.setInt(3, item.getQuantity());
                itemPs.setDouble(4, item.getPrice());
                itemPs.addBatch();
            }

            itemPs.executeBatch();

            String clearSql = "DELETE FROM cart_items WHERE user_id=?";
            PreparedStatement clearPs = conn.prepareStatement(clearSql);
            clearPs.setInt(1, userId);
            clearPs.executeUpdate();

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Order> getOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY order_id DESC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setPaymentStatus(rs.getString("payment_status"));
                order.setOrderDate(rs.getString("order_date"));
                order.setItems(getOrderItems(order.getOrderId()));

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

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
                order.setItems(getOrderItems(order.getOrderId()));

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET order_status=? WHERE order_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT oi.order_item_id, oi.order_id, oi.artwork_id, oi.quantity, oi.price, " +
                     "a.title, c.category_name " +
                     "FROM order_items oi " +
                     "JOIN artworks a ON oi.artwork_id = a.artwork_id " +
                     "JOIN categories c ON a.category_id = c.category_id " +
                     "WHERE oi.order_id=?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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