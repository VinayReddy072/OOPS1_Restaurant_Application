package com.restaurant.model;

public enum MealType {
    BREAKFAST("Morning", 7, 11),
    LUNCH("Afternoon", 11, 15),
    DINNER("Evening", 17, 22),
    LATE_NIGHT("Night", 22, 2);

    private final String timeOfDay;
    private final int startHour;
    private final int endHour;

    MealType(String timeOfDay, int startHour, int endHour) {
        this.timeOfDay = timeOfDay;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public boolean isAvailableAt(int hour) {
        if (this == LATE_NIGHT) {
            return hour >= startHour || hour < endHour;
        }
        return hour >= startHour && hour < endHour;
    }
}
