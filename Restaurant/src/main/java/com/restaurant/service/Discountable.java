package com.restaurant.service;

public sealed interface Discountable permits VIPDiscount, StudentDiscount, SeniorDiscount {
    double applyDiscount(double amount);

    default String getDiscountDescription() {
        return "Standard discount applied";
    }
}
