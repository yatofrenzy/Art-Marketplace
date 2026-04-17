package com.artmarketplace.dao.interfaces;

public interface OrderItemDAOInterface {
    boolean addOrderItem(int orderId, int artworkId, int quantity, double price);
}