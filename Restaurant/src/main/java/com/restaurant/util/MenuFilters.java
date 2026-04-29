package com.restaurant.util;

import com.restaurant.model.MenuItem;
import com.restaurant.model.MealType;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class MenuFilters {

    public static List<MenuItem> filterItems(List<MenuItem> items, Predicate<MenuItem> predicate) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static Predicate<MenuItem> priceGreaterThan(double price) {
        return item -> item.getPrice() > price;
    }

    public static Predicate<MenuItem> priceLessThan(double price) {
        return item -> item.getPrice() < price;
    }

    public static Predicate<MenuItem> isMealType(MealType mealType) {
        return item -> item.getMealType() == mealType;
    }

    public static Predicate<MenuItem> nameContains(String keyword) {
        final String lowerKeyword = keyword.toLowerCase();
        return item -> item.getName().toLowerCase().contains(lowerKeyword);
    }

    public static void printItems(List<MenuItem> items) {
        items.forEach(System.out::println);
    }

    public static double getTotalPrice(List<MenuItem> items) {
        return items.stream()
                   .mapToDouble(MenuItem::getPrice)
                   .sum();
    }
}
