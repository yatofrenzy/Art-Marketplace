package com.artmarketplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.artmarketplace.model.TopProduct;
import com.artmarketplace.utilities.DbConfig;

/**
 * Data Access Object (DAO) responsible for retrieving dashboard analytics statistics.
 * Part of the DAO layer in the MVC architecture.
 * Handles database operations for revenue calculation, order counts, customer volume, and monthly chart sales.
 */
public class AdminAnalyticsDAO {

    /**
     * Calculates the sum of all order totals in the database.
     * 
     * @return The total revenue amount as a double.
     */
    public double getTotalRevenue() {

        double revenue = 0;

        // SQL query: Sum the total_amount column from the orders table
        String sql =
        "SELECT IFNULL(SUM(total_amount), 0) AS revenue " +
        "FROM orders";

        // Try-with-resources: Auto-closes database connection, prepared statement, and result set
        try (
            Connection conn = DbConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            // Retrieve the summed value if result exists
            if (rs.next()) {

                revenue = rs.getDouble("revenue");

            }

        } catch (Exception e) {
            // Print error trace if query execution fails
            e.printStackTrace();

        }

        return revenue;
    }

    /**
     * Counts the total number of orders placed in the system.
     * 
     * @return The total order count.
     */
    public int getTotalOrders() {

        int total = 0;

        // SQL query: Count all records in the orders table
        String sql =
        "SELECT COUNT(*) AS total FROM orders";

        // Execute count query using prepared statement
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

    /**
     * Counts the total number of users registered with the 'customer' role.
     * 
     * @return The customer count.
     */
    public int getTotalCustomers() {

        int totalCustomers = 0;

        // SQL query: Count users filtered by customer role
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

    /**
     * Compiles monthly aggregated sales statistics for a specified calendar year.
     * 
     * @param year The target calendar year to filter sales data.
     * @return A list of 12 double sales values, representing monthly totals (index 0 for January, etc.).
     */
    public List<Double> getMonthlySales(int year) {

        List<Double> sales = new ArrayList<>();

        // Fill all 12 months with default double values of 0.0
        for(int i = 0; i < 12; i++) {

            sales.add(0.0);
        }

        // SQL query: Extract months and sum total order amount, filtering by specified year
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

            // Bind the year integer filter parameter to the first placeholder
            ps.setInt(1, year);

            ResultSet rs = ps.executeQuery();

            // Populate monthly values array based on database query results
            while (rs.next()) {

                int month = rs.getInt("month");

                double total = rs.getDouble("total");

                // Assign the summed sales to the corresponding index (month - 1)
                sales.set(month - 1, total);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return sales;
    }

    /**
     * Identifies the top 5 selling products based on total sales volume.
     * 
     * @return A list of the top 5 {@link TopProduct} items.
     */
    public List<TopProduct> getTopSellingProducts() {

        List<TopProduct> products =
                new ArrayList<>();

        // SQL query: Sum quantities sold per artwork, joining the artworks and order_items tables
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

            // Iterate over query result records to build product models
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