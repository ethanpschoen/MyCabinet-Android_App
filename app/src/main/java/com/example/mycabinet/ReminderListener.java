package com.example.mycabinet;

/*
The ReminderListener interface is implemented in the Kitchen class.
 */

public interface ReminderListener {
    // Methods to be implemented by the ReminderListener class
    void onFoodItemAdded(FoodItem item);
    void onFoodItemRemoved(FoodItem item);
    void onSettingsChanged();
}
