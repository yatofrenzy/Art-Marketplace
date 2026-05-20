package com.artmarketplace.model;

/**
 * Model class representing the analytics data for a top selling product.
 * Part of the Model layer in the MVC architecture.
 * Holds summary statistics displayed on the admin analytics dashboard.
 */
public class TopProduct {

    // Name of the top product
    private String name;
    // Image file path of the product
    private String image;
    // Total price revenue or product price
    private double price;

    /**
     * Default constructor for creating an empty TopProduct.
     */
    public TopProduct() {
    }

    /**
     * Parameterized constructor to initialize product name and image.
     * 
     * @param name  The product name string.
     * @param image The product image path.
     */
    public TopProduct(String name, String image) {
        this.name = name;
        this.image = image;
    }

    /**
     * Gets the product name.
     * 
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     * 
     * @param name The product name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product image path.
     * 
     * @return The product image path.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the product image path.
     * 
     * @param image The product image path to set.
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
     * Gets the product price or sales revenue value.
     * 
     * @return The price value.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price or sales revenue value.
     * 
     * @param price The price value to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}