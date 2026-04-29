package com.restaurant.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImmutableMenu {
    private final String menuName;
    private final List<MenuItem> items;

    public ImmutableMenu(String menuName, List<MenuItem> items) {
        if (menuName == null || menuName.isBlank()) {
            throw new IllegalArgumentException("Menu name cannot be null or blank");
        }
        this.menuName = menuName;
        this.items = new ArrayList<>(items);
    }

    public String getMenuName() {
        return menuName;
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public MenuItem[] getItemsAsArray() {
        MenuItem[] array = new MenuItem[items.size()];
        for (int i = 0; i < items.size(); i++) {
            array[i] = items.get(i);
        }
        return array;
    }

    public int getItemCount() {
        return items.size();
    }

    public MenuItem getItemAt(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return items.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu: ").append(menuName).append("\n");
        sb.append("Total Items: ").append(items.size()).append("\n");
        for (MenuItem item : items) {
            sb.append("  ").append(item).append("\n");
        }
        return sb.toString();
    }
}
