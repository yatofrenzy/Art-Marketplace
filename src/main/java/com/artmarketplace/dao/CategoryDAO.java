package com.artmarketplace.dao;

import com.artmarketplace.dao.interfaces.CategoryDAOInterface;
import com.artmarketplace.model.Category;
import com.artmarketplace.utilities.DBConnection;
import com.artmarketplace.utilities.DbConfig;

import java.sql.*;
import java.util.*;

public class CategoryDAO implements CategoryDAOInterface {

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        try {
            Connection conn = DbConfig.getConnection();
            String sql = "SELECT * FROM category";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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