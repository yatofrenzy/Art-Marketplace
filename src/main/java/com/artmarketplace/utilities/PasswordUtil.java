package com.artmarketplace.utilities;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return password;
    }

    public static boolean checkPassword(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }
}