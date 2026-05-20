package com.artmarketplace.model;

/**
 * Model class representing an Artwork Category.
 * Part of the Model layer in the MVC architecture.
 * Maps to the categories database table records.
 */
public class Category {
    // Unique identifier of the category
    private int categoryId;
    // Name tag of the category
    private String categoryName;

    /**
     * Gets the category unique identifier.
     * 
     * @return The category ID.
     */
    public int getCategoryId() { return categoryId; }

    /**
     * Sets the category unique identifier.
     * 
     * @param categoryId The category ID to set.
     */
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    /**
     * Gets the category name.
     * 
     * @return The category name string.
     */
    public String getCategoryName() { return categoryName; }

    /**
     * Sets the category name.
     * 
     * @param categoryName The category name string to set.
     */
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}