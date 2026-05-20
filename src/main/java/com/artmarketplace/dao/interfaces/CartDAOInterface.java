package com.artmarketplace.dao.interfaces;

/**
 * Interface defining Data Access Object (DAO) operations for shopping cart management.
 * Part of the DAO (Data Access Object) layer contract.
 * Describes methods for adding artworks to user shopping carts in the database.
 */
public interface CartDAOInterface {

    /**
     * Adds an artwork item to a user's shopping cart or updates the quantity if it already exists.
     * 
     * @param userId    The unique identifier of the user customer.
     * @param artworkId The unique identifier of the artwork product.
     * @param quantity  The count/amount of the artwork to add.
     * @return {@code true} if cart was updated successfully; {@code false} otherwise.
     */
    boolean addToCart(int userId, int artworkId, int quantity);
}