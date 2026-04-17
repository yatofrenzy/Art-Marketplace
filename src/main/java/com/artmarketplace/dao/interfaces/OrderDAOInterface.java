package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.Order;
import java.util.List;

public interface OrderDAOInterface {
    boolean createOrder(Order order);
    List<Order> getOrdersByUser(int userId);
}