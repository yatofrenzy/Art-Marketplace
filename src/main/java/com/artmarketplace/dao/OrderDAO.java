package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.OrderDAOInterface;
import com.artmarketplace.model.Order;
import com.artmarketplace.utilities.DBConnection;

import java.sql.*;
import java.util.*;

public class OrderDAO implements OrderDAOInterface {

    public boolean createOrder(Order order) {
        boolean status = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO orders(user_id, total_amt, order_date, status, payment_method, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmt());
            ps.setString(3, order.getOrderDate());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPaymentMethod());
            ps.setString(6, order.getPaymentStatus());
            ps.setString(7, order.getPaymentDate());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM orders WHERE user_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setTotalAmt(rs.getDouble("total_amt"));

                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}