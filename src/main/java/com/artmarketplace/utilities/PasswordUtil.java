package com.artmarketplace.utilities;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return password;
    }

    public static boolean checkPassword(String input, String stored) {
        return input.equals(stored);
    }
}