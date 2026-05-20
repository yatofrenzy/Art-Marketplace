package com.artmarketplace.dao.interfaces;

import com.artmarketplace.model.User;

/**
 * Interface defining Data Access Object (DAO) operations for User management.
 * Part of the DAO (Data Access Object) layer contract.
 * Describes methods for registering users and performing database login validation checks.
 */
public interface UserDAOInterface {

    /**
     * Registers a new user account profile in the database.
     * 
     * @param user The {@link User} object containing account details to save.
     * @return {@code true} if registration is successful; {@code false} otherwise.
     */
    boolean registerUser(User user);

    /**
     * Authenticates a user login attempt by checking email and password credentials.
     * 
     * @param email    The email credential input.
     * @param password The raw password credential input.
     * @return The authenticated {@link User} profile model if valid; {@code null} otherwise.
     */
    User login(String email, String password);
}

