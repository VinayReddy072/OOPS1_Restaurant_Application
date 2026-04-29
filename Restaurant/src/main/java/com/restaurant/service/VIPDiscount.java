package com.restaurant.service;

public final class VIPDiscount implements Discountable {
    private final double discountRate = 0.20;

    @Override
    public double applyDiscount(double amount) {
        return amount * (1 - discountRate);
    }

    @Override
    public String getDiscountDescription() {
        return "VIP 20% discount";
    }
}
