package com.restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRecord(
    String orderId,
    String customerName,
    List<MenuItem> items,
    LocalDateTime orderTime
) {
    public OrderRecord {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        items = List.copyOf(items);
    }

    public double getTotalPrice() {
        return items.stream()
                   .mapToDouble(MenuItem::getPrice)
                   .sum();
    }

    public int getItemCount() {
        return items.size();
    }
}
