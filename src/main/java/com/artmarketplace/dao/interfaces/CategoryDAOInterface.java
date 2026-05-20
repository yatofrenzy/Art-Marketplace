package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.Category;
import java.util.List;

/**
 * Interface defining Data Access Object (DAO) operations for Category management.
 * Part of the DAO (Data Access Object) layer contract.
 * Describes methods for retrieving all available artwork categories.
 */
public interface CategoryDAOInterface {

    /**
     * Retrieves all available categories of artwork from the database.
     * 
     * @return A {@link List} of all {@link Category} models.
     */
    List<Category> getAllCategories();
}