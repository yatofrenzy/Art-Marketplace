package com.artmarketplace.model;

/**
 * Model class representing a specific item inside a customer order.
 * Part of the Model layer in the MVC architecture.
 * Maps to the order_items table database records.
 */
public class OrderItem {
    // Unique identifier of the order item entry
    private int orderItemId;
    // ID of the parent order
    private int orderId;
    // ID of the artwork purchased
    private int artworkId;
    // Title/Name of the purchased artwork
    private String title;
    // Category name of the artwork (e.g., Painting)
    private String categoryName;
    // Quantity of the artwork purchased in this item line
    private int quantity;
    // Price of the artwork at the time of purchase
    private double price;

    /**
     * Gets the unique identifier for the order item.
     * 
     * @return The order item ID.
     */
    public int getOrderItemId() {
        return orderItemId;
    }

    /**
     * Sets the unique identifier for the order item.
     * 
     * @param orderItemId The order item ID to set.
     */
    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * Gets the parent order identifier.
     * 
     * @return The parent order ID.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the parent order identifier.
     * 
     * @param orderId The parent order ID to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the artwork identifier.
     * 
     * @return The artwork ID.
     */
    public int getArtworkId() {
        return artworkId;
    }

    /**
     * Sets the artwork identifier.
     * 
     * @param artworkId The artwork ID to set.
     */
    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    /**
     * Gets the title of the artwork.
     * 
     * @return The artwork title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the artwork.
     * 
     * @param title The artwork title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the category name of the artwork.
     * 
     * @return The category name.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the category name of the artwork.
     * 
     * @param categoryName The category name to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the quantity of this item purchased.
     * 
     * @return The quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this item purchased.
     * 
     * @param quantity The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the price of the artwork at the time of purchase.
     * 
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the artwork at the time of purchase.
     * 
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}