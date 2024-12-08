package com.example.mycabinet;

import java.time.LocalDate;
import java.util.ArrayList;

/*
The Kitchen model class represents the kitchen in the application.
The Kitchen holds all of the data for the Chef, and is the central part of the application.
 */

public class Kitchen {

    // Singleton instance of the Kitchen class
    private static Kitchen instance;

    /*
    Member variables

    sections: ArrayList of FoodSection objects representing the kitchen sections
    settings: Preferences object representing the user's settings
     */
    private ArrayList<FoodSection> sections;
    private Preferences settings;


    // Constructor to initialize the object
    private Kitchen() {
        sections = new ArrayList<FoodSection>();
        settings = new Preferences();
    }

    // Public method to get the singleton instance of the Kitchen class
    public static Kitchen getInstance() {
        // Create new object if not already created
        if (instance == null) {
            instance = new Kitchen();
            // Autogenerate initial objects for Kitchen
            generateKitchen();
        }

        return instance;
    }


    // Methods to add and remove FoodSection objects from the kitchen

    public void addSection(FoodSection section) {
        sections.add(section);
    }

    public void removeSection(FoodSection section) {
        boolean removed = sections.remove(section);
    }

    public void clearSections() {
        sections.clear();
    }

    // Method to get a FoodSection object by name
    public FoodSection getSection(String name) {
        // Iterate through each FoodSection in the Kitchen
        for (FoodSection section : sections) {
            // If the name matches, return the FoodSection object
            if (section.getSectionName().equals(name)) {
                return section;
            }
        }
        // If no matching FoodSection is found, return null
        return null;
    }

    // Method to remove a FoodItem object from the kitchen
    public void removeItem(FoodItem item) {
        // Iterate through each section
        for (FoodSection section : sections) {
            // (Try to) delete the FoodItem object from the section
            section.removeFoodItem(item);
        }
    }

    // Getters and setters

    public ArrayList<FoodSection> getSections() {
        return sections;
    }


    public void setSettings(Preferences settings) {
        this.settings = settings;
    }

    public Preferences getSettings() {
        return settings;
    }


    // Static method to generate initial Kitchen objects
    private static void generateKitchen() {
        // Get Kitchen object
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

        // Iterate through each section and create FoodSection objects
        for (String[] sectionData : sectionsWithItems) {
            String sectionName = sectionData[0];
            FoodSection section = new FoodSection(sectionName);

            // Iterate through each item in the section and create FoodItem objects
            for (int i = 1; i < sectionData.length; i++) {
                String foodName = sectionData[i];

                // If the food name is not empty, create a FoodItem object and add it to the section
                if (!foodName.isEmpty()) {
                    // Generate a future date for the FoodItem
                    LocalDate futureDate = generateFutureDate(i);
                    section.addFoodItem(new FoodItem(foodName, futureDate));
                }
            }

            // Add the FoodSection object to the Kitchen
            kitchen.addSection(section);
        }
    }

    // Helper method to generate a future date
    private static LocalDate generateFutureDate(int offsetDays) {
        return LocalDate.now().plusDays(offsetDays); // Add offset days to the current date
    }
}
