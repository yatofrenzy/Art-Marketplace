package com.artmarketplace.utilities;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

/**
 * Utility class providing validation methods for forms and business rules.
 * Part of the Utility layer in the MVC architecture.
 * Contains regex-based validation helpers for email, password, phone numbers,
 * file uploads, age checks, and name checks.
 */
public class ValidationUtil {

    /**
     * Checks if a string value is null or empty (containing only whitespace).
     * 
     * @param value The string to check.
     * @return {@code true} if the string is null, empty, or whitespace-only; {@code false} otherwise.
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks if a string contains only alphabetic characters (A-Z, a-z).
     * 
     * @param value The string to check.
     * @return {@code true} if the string contains only letters; {@code false} otherwise.
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }

    /**
     * Checks if a string starts with a letter and contains only letters and numbers (alphanumeric).
     * 
     * @param value The string to check.
     * @return {@code true} if valid; {@code false} otherwise.
     */
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    /**
     * Checks if a gender string matches "male" or "female" (case-insensitive).
     * 
     * @param value The string to check.
     * @return {@code true} if match is found; {@code false} otherwise.
     */
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    /**
     * Validates an email address against a standard email regular expression.
     * 
     * @param email The email string to validate.
     * @return {@code true} if valid; {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        // Regex pattern definition for standard email format matching
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates if a phone number starts with '98' and contains exactly 10 digits.
     * 
     * @param number The phone number string to validate.
     * @return {@code true} if valid; {@code false} otherwise.
     */
    public static boolean isValidPhoneNumber(String number) {
        // Regex checking for starting 98 followed by 8 digit numbers (total 10 digits)
        return number != null && number.matches("^98\\d{8}$");
    }

    /**
     * Validates if a password meets the strength criteria:
     * Contains at least 1 capital letter, 1 number, 1 special character symbol,
     * and has a minimum length of 6 characters.
     * 
     * @param password The password string to validate.
     * @return {@code true} if valid; {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        // Regex validation for uppercase letter, digit, special character, minimum 6 characters
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password != null && password.matches(passwordRegex);
    }

    /**
     * Validates if a uploaded file Part has a valid image extension (jpg, jpeg, png, gif).
     * 
     * @param imagePart The HTTP servlet Part object containing the file upload.
     * @return {@code true} if the extension is valid; {@code false} otherwise.
     */
    public static boolean isValidImageExtension(Part imagePart) {
        // Return false if imagePart is null or filename is missing
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        // Extract lowercase representation of submitted filename
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        // Check if the filename ends with standard image formats
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /**
     * Validates if password and retype password match.
     * 
     * @param password        The initial password input.
     * @param retypePassword  The confirmation password input.
     * @return {@code true} if matching; {@code false} otherwise.
     */
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }

    /**
     * Validates if the date of birth is at least 16 years before today.
     * 
     * @param dob The date of birth {@link LocalDate}.
     * @return {@code true} if age is >= 16; {@code false} otherwise.
     */
    public static boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        // Grab current system date
        LocalDate today = LocalDate.now();
        // Calculate period difference in years and compare
        return Period.between(dob, today).getYears() >= 16;
    }
}
