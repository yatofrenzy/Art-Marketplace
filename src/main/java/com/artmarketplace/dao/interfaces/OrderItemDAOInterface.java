package com.artmarketplace.dao.interfaces;

/**
 * Interface defining Data Access Object (DAO) operations for OrderItem management.
 * Part of the DAO (Data Access Object) layer contract.
 * Describes methods for inserting detailed artwork item lines belonging to specific customer orders.
 */
public interface OrderItemDAOInterface {

    /**
     * Adds an artwork item to a created order record details list.
     * 
     * @param orderId   The parent order unique ID identifier.
     * @param artworkId The unique ID of the artwork purchased.
     * @param quantity  The count/number of the artwork units ordered.
     * @param price     The price rate of the artwork at checkout time.
     * @return {@code true} if successful; {@code false} otherwise.
     */
    boolean addOrderItem(int orderId, int artworkId, int quantity, double price);
}