package com.artmarketplace.model;

import java.util.List;

/**
 * Model class representing a customer checkout Order record.
 * Part of the Model layer in the MVC architecture.
 * Maps to the orders table database rows and contains order summary properties.
 */
public class Order {
    // Unique identifier of the order
    private int orderId;
    // ID of the user customer who placed the order
    private int userId;
    // Total cost amount of all artworks purchased in this order
    private double totalAmount;
    // Status flag of the order (e.g., Pending, Approved, Shipped, Delivered)
    private String orderStatus;
    // Selected payment mechanism (e.g., Cash, Card, E-Wallet)
    private String paymentMethod;
    // Payment status marker (e.g., Paid, Pending)
    private String paymentStatus;
    // Timestamp string when the order transaction took place
    private String orderDate;
    // Name of the customer who ordered (used in admin lists)
    private String customerName;
    // Collection of items associated with this specific order
    private List<OrderItem> items;

    /**
     * Gets the order unique identifier.
     * 
     * @return The order ID.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order unique identifier.
     * 
     * @param orderId The order ID to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the user identifier of the purchaser.
     * 
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier of the purchaser.
     * 
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the total monetary amount of the order.
     * 
     * @return The total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total monetary amount of the order.
     * 
     * @param totalAmount The total amount to set.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets the order status.
     * 
     * @return The order status string.
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the order status.
     * 
     * @param orderStatus The order status string to set.
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Gets the payment method used.
     * 
     * @return The payment method.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method used.
     * 
     * @param paymentMethod The payment method to set.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the payment status.
     * 
     * @return The payment status string.
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status.
     * 
     * @param paymentStatus The payment status string to set.
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Gets the order placement date.
     * 
     * @return The order date string.
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order placement date.
     * 
     * @param orderDate The order date string to set.
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the customer's display name.
     * 
     * @return The customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer's display name.
     * 
     * @param customerName The customer name to set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the list of items purchased under this order.
     * 
     * @return The list of {@link OrderItem}s.
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Sets the list of items purchased under this order.
     * 
     * @param items The list of {@link OrderItem}s to set.
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}