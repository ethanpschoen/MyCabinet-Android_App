package com.example.mycabinet;

import android.widget.ImageView;

import java.util.ArrayList;

/*
The FoodSection model class represents a section of the Kitchen.
It has a unique name, a list of FoodItems, a color, an icon, and a size.
The FoodSection is a customizable way for the Chef to organize their FoodItems.
 */

public class FoodSection {

    /*
    Member variables

    sectionName: String representing the name of the section
    sectionItems: ArrayList of FoodItem objects representing the items in the section
    sectionColor: String representing the color of the section (not implemented yet)
    sectionIcon: ImageView representing the icon of the section (not implemented yet)
    sectionSize: int representing the number of items in the section
     */
    private String sectionName;
    private ArrayList<FoodItem> sectionItems;
    private String sectionColor;
    private ImageView sectionIcon;
    private int sectionSize;


    // Constructor to initialize the object
    public FoodSection(String sectionName) {
        this.sectionName = sectionName;
        this.sectionItems = new ArrayList<FoodItem>();
        this.sectionColor = "#000000";
        this.sectionIcon = null;
        this.sectionSize = 0;
    }


    // Getters and setters

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<FoodItem> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(ArrayList<FoodItem> sectionItems) {
        this.sectionItems = sectionItems;
    }

    public String getSectionColor() {
        return sectionColor;
    }

    public void setSectionColor(String sectionColor) {
        this.sectionColor = sectionColor;
    }

    public ImageView getSectionIcon() {
        return sectionIcon;
    }

    public void setSectionIcon(ImageView sectionIcon) {
        this.sectionIcon = sectionIcon;
    }

    public int getSectionSize() {
        return sectionSize;
    }

    public void setSectionSize(int sectionSize) {
        this.sectionSize = sectionSize;
    }


    // Methods to add and remove items from the section

    public void addFoodItem(FoodItem item) {
        sectionItems.add(item);
        sectionSize++;
    }

    public void removeFoodItem(FoodItem item) {
        boolean removed = sectionItems.remove(item);
        if (removed) {
            sectionSize--;
        }
    }

    public void clearFoodItems() {
        sectionItems.clear();
        sectionSize = 0;
    }
}
