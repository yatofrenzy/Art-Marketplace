package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.Order;
import java.util.List;

/**
 * Interface defining Data Access Object (DAO) operations for Order management.
 * Part of the DAO (Data Access Object) layer contract.
 * Describes methods for creating orders and fetching orders submitted by users.
 */
public interface OrderDAOInterface {

    /**
     * Creates a new customer checkout order record in the database.
     * 
     * @param order The {@link Order} model containing order details and items.
     * @return {@code true} if order is created successfully; {@code false} otherwise.
     */
    boolean createOrder(Order order);

    /**
     * Retrieves all order history records for a specific customer user.
     * 
     * @param userId The unique identifier of the user customer.
     * @return A {@link List} of {@link Order} items placed by the user.
     */
    List<Order> getOrdersByUser(int userId);
}