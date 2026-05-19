package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.utilities.DbConfig;

public class AdminAnalyticsDAO {

    // =========================================
    // TOTAL REVENUE
    // =========================================
    public double getTotalRevenue() {

        double revenue = 0;

        String sql =
        "SELECT IFNULL(SUM(total_amount), 0) AS revenue " +
        "FROM orders";

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                revenue = rs.getDouble("revenue");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return revenue;
    }

    // =========================================
    // TOTAL ORDERS
    // =========================================
    public int getTotalOrders() {

        int total = 0;

        String sql =
        "SELECT COUNT(*) AS total FROM orders";

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                total = rs.getInt("total");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return total;
    }

    // =========================================
    // TOTAL CUSTOMERS
    // =========================================
    public int getTotalCustomers() {

        int totalCustomers = 0;

        String sql =
        "SELECT COUNT(*) AS total " +
        "FROM users " +
        "WHERE role = 'customer'";

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                totalCustomers = rs.getInt("total");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return totalCustomers;
    }

    // =========================================
    // MONTHLY SALES
    // =========================================
    public List<Double> getMonthlySales() {

        List<Double> sales = new ArrayList<>();

        String sql =
        "SELECT MONTH(order_date) AS month, " +
        "SUM(total_amount) AS total " +
        "FROM orders " +
        "GROUP BY MONTH(order_date) " +
        "ORDER BY MONTH(order_date)";

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                sales.add(
                    rs.getDouble("total")
                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return sales;
    }

    // =========================================
    // TOP SELLING PRODUCTS
    // =========================================
    public List<String> getTopSellingProducts() {

        List<String> products = new ArrayList<>();

        String sql =
        		"SELECT a.title, SUM(oi.quantity) AS total_sold " +
        		"FROM order_items oi " +
        		"JOIN artworks a ON oi.artwork_id = a.artwork_id " +
        		"GROUP BY a.title " +
        		"ORDER BY total_sold DESC LIMIT 5";

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                products.add(
                    rs.getString("title")
                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return products;
    }

}