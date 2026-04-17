package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.Category;
import java.util.List;

public interface CategoryDAOInterface {
    List<Category> getAllCategories();
}