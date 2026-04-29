package com.restaurant.service;

public final class StudentDiscount implements Discountable {
    private final double discountRate = 0.10;

    @Override
    public double applyDiscount(double amount) {
        return amount * (1 - discountRate);
    }

    @Override
    public String getDiscountDescription() {
        return "Student 10% discount";
    }
}
