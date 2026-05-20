package com.artmarketplace.model;

/**
 * Model class representing a User (Customer or Admin) in the system.
 * Part of the Model layer in the MVC architecture.
 * Represents database rows in the users table and holds credentials/profile attributes.
 */
public class User {
    // Unique identifier of the user
    private int userId;
    // Full name of the user
    private String name;
    // Email address used for login and notifications
    private String email;
    // Hashed password string of the user
    private String password;
    // Role descriptor of the user (e.g., admin, customer)
    private String role;
    // Optional contact number string
    private String contactNumber;
    // File upload directory path for the user's profile image
    private String profileImage;
    // Primary phone number string of the user
    private String phone;
    // Registration status of the account (e.g., Pending, Approved, Rejected)
    private String accountStatus;
    // Total order count placed by the customer (used in admin reports)
    private int orderCount;

    /**
     * Gets the user unique identifier.
     * 
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user unique identifier.
     * 
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the full name of the user.
     * 
     * @return The user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the user.
     * 
     * @param name The user name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the registered email address.
     * 
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the registered email address.
     * 
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Gets the hashed password string.
     * 
     * @return The hashed password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password (hashed string).
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Gets the user's role (e.g., "customer" or "admin").
     * 
     * @return The user role string.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role (e.g., "customer" or "admin").
     * 
     * @param role The user role string to set.
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * Gets the user's optional contact number.
     * 
     * @return The contact number string.
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Sets the user's optional contact number.
     * 
     * @param contactNumber The contact number string to set.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Gets the path to the user's profile image.
     * 
     * @return The profile image path string.
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * Sets the path to the user's profile image.
     * 
     * @param profileImage The profile image path string to set.
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * Gets the primary phone number.
     * 
     * @return The phone number string.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the primary phone number.
     * 
     * @param phone The phone number string to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the administrative account status (Pending, Approved, etc.).
     * 
     * @return The status string.
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the administrative account status (Pending, Approved, etc.).
     * 
     * @param accountStatus The status string to set.
     */
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Gets the total number of orders placed by the user.
     * 
     * @return The order count.
     */
    public int getOrderCount() {
        return orderCount;
    }

    /**
     * Sets the total number of orders placed by the user.
     * 
     * @param orderCount The order count to set.
     */
    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}