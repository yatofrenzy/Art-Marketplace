package com.artmarketplace.utilities;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for secure password hashing and verification.
 * Part of the Utility layer in the MVC architecture.
 * Utilizes the jBCrypt library to hash user passwords and verify login inputs safely.
 */
public class PasswordUtil {

    /**
     * Hashes a plain-text password using a generated salt value.
     * 
     * @param plainPassword The plain text password to be hashed.
     * @return The hashed password string containing the salt and hash.
     */
    public static String hashPassword(String plainPassword) {
        // Generate a salt and hash the plain text password using BCrypt
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Checks if a plain-text password matches a previously hashed password.
     * 
     * @param plainPassword  The plain text password candidate to verify.
     * @param hashedPassword The existing hashed password from the database.
     * @return {@code true} if the passwords match; {@code false} otherwise.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // Use BCrypt's internal verification logic to match password candidate with saved hash
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}