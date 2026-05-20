package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.TopProduct;
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
 public List<Double> getMonthlySales(int year) {

     List<Double> sales = new ArrayList<>();

     // Fill all 12 months with 0
     for(int i = 0; i < 12; i++) {

         sales.add(0.0);
     }

     String sql =
         "SELECT MONTH(order_date) AS month, " +
         "SUM(total_amount) AS total " +
         "FROM orders " +
         "WHERE YEAR(order_date) = ? " +
         "GROUP BY MONTH(order_date) " +
         "ORDER BY MONTH(order_date)";

     try (

         Connection conn = DbConfig.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)

     ) {

         // SET YEAR
         ps.setInt(1, year);

         ResultSet rs = ps.executeQuery();

         while (rs.next()) {

             int month = rs.getInt("month");

             double total = rs.getDouble("total");

             // month-1 because array starts from 0
             sales.set(month - 1, total);
         }

     } catch (Exception e) {

         e.printStackTrace();
     }

     return sales;
 }

    // =========================================
    // TOP SELLING PRODUCTS
    // =========================================
    public List<TopProduct> getTopSellingProducts() {

        List<TopProduct> products =
                new ArrayList<>();

        String sql =
            "SELECT a.title, a.image_path, a.price," +
            "SUM(oi.quantity) AS total_sold " +
            "FROM order_items oi " +
            "JOIN artworks a " +
            "ON oi.artwork_id = a.artwork_id " +
            "GROUP BY a.title, a.image_path, a.price " +
            "ORDER BY total_sold DESC " +
            "LIMIT 5";

        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while(rs.next()) {

                TopProduct product =
                        new TopProduct();

                product.setName(
                        rs.getString("title"));

                product.setImage(
                        rs.getString("image_path"));
                
                product.setPrice(
                	    rs.getDouble("price"));

                products.add(product);
            }

        } catch(Exception e) {

            e.printStackTrace();

        }

        return products;
    }
}