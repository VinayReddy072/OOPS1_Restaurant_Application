package com.restaurant.model;

import java.util.Objects;

public class MenuItem {
    private final String name;
    private final double price;
    private final MealType mealType;

    public MenuItem(String name, double price, MealType mealType) {
        this.name = name;
        this.price = price;
        this.mealType = mealType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public MealType getMealType() {
        return mealType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Double.compare(price, menuItem.price) == 0 &&
               Objects.equals(name, menuItem.name) &&
               mealType == menuItem.mealType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, mealType);
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f (%s)", name, price, mealType);
    }
}
