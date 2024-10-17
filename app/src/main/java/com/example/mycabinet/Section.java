package com.example.mycabinet;

import android.widget.ImageView;

import java.util.ArrayList;

public class Section {

    String sectionName;
    ArrayList<FoodItem> sectionItems;
    String sectionColor;
    ImageView sectionImage;
    int sectionSize;

    public Section(String sectionName) {
        this.sectionName = sectionName;
        this.sectionItems = new ArrayList<FoodItem>();
        this.sectionColor = "#000000";
        this.sectionImage = null;
        this.sectionSize = 0;
    }

}
