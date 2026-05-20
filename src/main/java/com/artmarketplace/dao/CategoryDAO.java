package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.CategoryDAOInterface;
import com.artmarketplace.model.Category;
import com.artmarketplace.utilities.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) implementing Category retrieval database queries.
 * Part of the DAO layer in the MVC architecture.
 * Implements the {@link CategoryDAOInterface} interface contract for DB actions.
 */
public class CategoryDAO implements CategoryDAOInterface {

    /**
     * Retrieves all artwork categories from the database in alphabetical order of category name.
     * 
     * @return A {@link List} of all {@link Category} models.
     */
    @Override
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        // SQL query to fetch all category records sorted alphabetically
        String sql = "SELECT * FROM categories ORDER BY category_name ASC";

        // Try-with-resources auto-closes the connection, statement, and result set
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Loop through the results and construct Category models
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                list.add(c);
            }

        } catch (Exception e) {
            // Logs SQL syntax or database connection errors
            e.printStackTrace();
        }

        return list;
    }
}