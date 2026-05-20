package com.artmarketplace.model;

/**
 * Model class representing an item added to a user's shopping cart.
 * Part of the Model layer in the MVC architecture.
 * Holds product details (artwork ID, title, price, quantity, image path) and computes the total.
 */
public class CartItem {
    // Unique identifier of the cart item entry
    private int cartItemId;
    // ID of the artwork associated with the cart item
    private int artworkId;
    // Title/Name of the artwork
    private String title;
    // Price of a single unit of the artwork
    private double price;
    // Quantity of the artwork selected by the user
    private int quantity;
    // Stored image path for displaying in the shopping cart view
    private String imagePath;

    /**
     * Gets the cart item identifier.
     * 
     * @return The cart item ID.
     */
    public int getCartItemId() {
        return cartItemId;
    }

    /**
     * Sets the cart item identifier.
     * 
     * @param cartItemId The cart item ID to set.
     */
    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
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
     * Gets the artwork title.
     * 
     * @return The artwork title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the artwork title.
     * 
     * @param title The artwork title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the single unit price of the artwork.
     * 
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the single unit price of the artwork.
     * 
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the quantity of this item in the cart.
     * 
     * @return The quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this item in the cart.
     * 
     * @param quantity The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the image path for the artwork.
     * 
     * @return The image path string.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path for the artwork.
     * 
     * @param imagePath The image path string to set.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Calculates and returns the total price for this cart item based on price and quantity.
     * 
     * @return The computed total price.
     */
    public double getTotal() {
        // Multiply unit price by selected quantity to get subtotal
        return price * quantity;
    }
}