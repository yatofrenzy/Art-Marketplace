package com.artmarketplace.dao;

import com.artmarketplace.model.Order;
import com.artmarketplace.utilities.DbConfig;

import java.sql.*;
import java.util.*;

public class OrderDAO {

    //  CREATE ORDER (returns order_id)
    public int createOrder(Order order) {
        int orderId = -1;

        try (Connection conn = DbConfig.getConnection()) {

            String sql = "INSERT INTO orders(user_id, total_amount, order_date, status, payment_method, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmt());
            ps.setString(3, order.getOrderDate());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPaymentMethod());
            ps.setString(6, order.getPaymentStatus());
            ps.setString(7, order.getPaymentDate());

            ps.executeUpdate();

            //  GET GENERATED ID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderId;
    }

    //  GET ORDERS BY USER
    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();

        try (Connection conn = DbConfig.getConnection()) {

            String sql = "SELECT * FROM orders WHERE user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id")); // ✅ FIXED
                o.setTotalAmt(rs.getDouble("total_amount"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}