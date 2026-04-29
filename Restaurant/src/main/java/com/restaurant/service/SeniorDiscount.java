package com.restaurant.service;

public final class SeniorDiscount implements Discountable {
    private final double discountRate = 0.15;

    @Override
    public double applyDiscount(double amount) {
        return amount * (1 - discountRate);
    }

    @Override
    public String getDiscountDescription() {
        return "Senior 15% discount";
    }
}
