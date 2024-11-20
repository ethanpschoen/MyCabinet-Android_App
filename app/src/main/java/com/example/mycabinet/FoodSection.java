package com.example.mycabinet;

import android.widget.ImageView;

import java.util.ArrayList;

public class FoodSection {

    private String sectionName;
    private ArrayList<FoodItem> sectionItems;
    private String sectionColor;
    private ImageView sectionIcon;
    private int sectionSize;

    public FoodSection(String sectionName) {
        this.sectionName = sectionName;
        this.sectionItems = new ArrayList<FoodItem>();
        this.sectionColor = "#000000";
        this.sectionIcon = null;
        this.sectionSize = 0;
    }

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

    public void addFoodItem(FoodItem item) {
        sectionItems.add(item);
        sectionSize++;
    }

    public void removeFoodItem(FoodItem item) {
        sectionItems.remove(item);
        sectionSize--;
    }

    public void clearFoodItems() {
        sectionItems.clear();
        sectionSize = 0;
    }
}
