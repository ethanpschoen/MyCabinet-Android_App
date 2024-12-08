package com.example.mycabinet;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class Kitchen {

    private static Kitchen instance;

    private ArrayList<FoodSection> sections;
    private Preferences settings;

    private Kitchen() {
        sections = new ArrayList<FoodSection>();
        settings = new Preferences();
    }

    public static Kitchen getInstance() {
        if (instance == null) {
            instance = new Kitchen();
            generateKitchen();
        }

        return instance;
    }

    public void addSection(FoodSection section) {
        sections.add(section);
    }

    public FoodSection getSection(String name) {
        for (FoodSection section : sections) {
            if (section.getSectionName().equals(name)) {
                return section;
            }
        }
        return null;
    }

    public void removeSection(FoodSection section) {
        boolean removed = sections.remove(section);
    }

    public void clearSections() {
        sections.clear();
    }

    public ArrayList<FoodSection> getSections() {
        return sections;
    }

    public void removeItem(FoodItem item) {
        for (FoodSection section : sections) {
            section.removeFoodItem(item);
        }
    }

    public void setSettings(Preferences settings) {
        this.settings = settings;
    }

    public Preferences getSettings() {
        return settings;
    }

    private static void generateKitchen() {
        Kitchen kitchen = Kitchen.getInstance();

        String[][] sectionsWithItems = {
                {"Fruits", "Apple", "Banana", "Orange", "Strawberry", "Grapes"},
                {"Vegetables", "Carrot", "Broccoli", "", "Potato", "Tomato"},
                {"Dairy", "Milk", "Cheese", "Butter", "Yogurt", "Cream"},
                {"Frozen Foods", "Frozen Pizza", "Ice Cream", "", "", "Chicken Nuggets"},
                {"Snacks", "Chips", "Cookies", "Chocolate", "Popcorn", "Granola Bars"},
                {"Beverages", "Water", "Juice", "", "Coffee", ""},
                {"Condiments", "", "", "", "", ""},
                {"Meat & Seafood", "Chicken Breast", "", "Beef Steak", "", "Pork Chops"},
                {"Bakery", "Bread", "", "Donut", "", ""},
                {"Grains & Pasta", "Rice", "Pasta", "Quinoa", "Couscous", "Oats"}
        };

        for (String[] sectionData : sectionsWithItems) {
            String sectionName = sectionData[0];
            FoodSection section = new FoodSection(sectionName);

            for (int i = 1; i < sectionData.length; i++) {
                String foodName = sectionData[i];

                if (!foodName.isEmpty()) {
                    LocalDate futureDate = generateFutureDate(i);
                    section.addFoodItem(new FoodItem(foodName, futureDate));
                }
            }

            kitchen.addSection(section);
        }
    }

    private static LocalDate generateFutureDate(int offsetDays) {
        return LocalDate.now().plusDays(offsetDays); // Add offset days to the current date
    }
}
