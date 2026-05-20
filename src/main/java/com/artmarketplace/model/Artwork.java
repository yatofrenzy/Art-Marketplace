package com.artmarketplace.model;

/**
 * Model class representing an Artwork item in the marketplace.
 * Part of the Model layer in the MVC architecture.
 * Represents the database record in the artworks table.
 */
public class Artwork {
    // Unique identifier of the artwork
    private int artworkId;
    // Category identifier associated with the artwork
    private int categoryId;
    // Title/Name of the artwork
    private String title;
    // Description detailing the artwork background or style
    private String description;
    // Selling price of the artwork
    private double price;
    // File upload directory path for the artwork image
    private String imagePath;

    /**
     * Gets the unique artwork identifier.
     * 
     * @return The artwork ID.
     */
    public int getArtworkId() {
        return artworkId;
    }

    /**
     * Sets the unique artwork identifier.
     * 
     * @param artworkId The artwork ID to set.
     */
    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    /**
     * Gets the category identifier linked to this artwork.
     * 
     * @return The category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category identifier linked to this artwork.
     * 
     * @param categoryId The category ID to set.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
     * Gets the description details of the artwork.
     * 
     * @return The artwork description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description details of the artwork.
     * 
     * @param description The artwork description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price of the artwork.
     * 
     * @return The artwork price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the artwork.
     * 
     * @param price The artwork price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the path to the stored artwork image.
     * 
     * @return The artwork image path.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the path to the stored artwork image.
     * 
     * @param imagePath The artwork image path to set.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}