package com.artmarketplace.dao.interfaces;

public interface CartDAOInterface {
    boolean addToCart(int userId, int artworkId, int quantity);
}