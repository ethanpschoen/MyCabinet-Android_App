package com.example.mycabinet;

public interface ReminderListener {
    void onFoodItemAdded(FoodItem item);
    void onFoodItemRemoved(FoodItem item);
    void onSettingsChanged();
}
