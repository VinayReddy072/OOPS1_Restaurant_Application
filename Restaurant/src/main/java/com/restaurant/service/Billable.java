package com.restaurant.service;

public interface Billable {

    double calculateTotal();

    default double calculateTotalWithTip(double tipPercentage) {
        return calculateTotal() * (1 + validateTipPercentage(tipPercentage) / 100);
    }

    default double calculateTotalWithTax() {
        return calculateTotal() * (1 + getDefaultTaxRate());
    }

    default double calculateGrandTotal(double tipPercentage) {
        double subtotal = calculateTotal();
        double tax = subtotal * getDefaultTaxRate();
        double tip = subtotal * (validateTipPercentage(tipPercentage) / 100);
        return formatAmount(subtotal + tax + tip);
    }

    private double validateTipPercentage(double tip) {
        if (tip < 0 || tip > 100) {
            throw new IllegalArgumentException("Tip percentage must be between 0 and 100");
        }
        return tip;
    }

    private double formatAmount(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    static double getDefaultTaxRate() {
        return 0.08;
    }

    static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
}
