package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.User;

public interface UserDAOInterface {
    boolean registerUser(User user);
    User login(String email, String password);
}

