package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.CategoryDAOInterface;
import com.artmarketplace.model.Category;
import com.artmarketplace.utilities.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements CategoryDAOInterface {

    @Override
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM categories ORDER BY category_name ASC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}