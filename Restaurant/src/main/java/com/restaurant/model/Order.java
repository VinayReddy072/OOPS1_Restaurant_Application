package com.restaurant.model;

import com.restaurant.service.Billable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Billable {
    private final String orderId;
    private final List<MenuItem> items;

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void addItems(MenuItem... items) {
        for (MenuItem item : items) {
            this.items.add(item);
        }
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public double calculateTotal() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Items:\n");
        for (MenuItem item : items) {
            sb.append("  - ").append(item.toString()).append("\n");
        }
        sb.append("Total: ").append(Billable.formatCurrency(calculateTotal()));
        return sb.toString();
    }
}
