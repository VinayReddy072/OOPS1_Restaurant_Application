package com.restaurant.exception;

public class OrderNotFoundException extends RuntimeException {

    // Constructor that handles both general messages and order IDs
    public OrderNotFoundException(String messageOrOrderId) {
        super("Order not found with ID: " + messageOrOrderId); // Default to "Order not found with ID" for an orderId
    }

    // Constructor with message and cause (for exception chaining)
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
