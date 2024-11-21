package com.example.mycabinet;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;

public class FoodItem {
    private Context mContext;
    private List<String> mFoodItemList;
    private List<String> mFoodNameItemList;
    private List<String> mFoodItemNotesList;

    String foodItemCount;
    String fdate;

    String description;


    public FoodItem(Context context) {
        mContext = context;
        mFoodItemList = new ArrayList<>();
        mFoodNameItemList = new ArrayList<>();
        mFoodItemNotesList = new ArrayList<>();

        this.foodItemCount = foodItemCount;
        this.fdate = fdate;

        this.description = description;
    }


    public void addItem(String item) throws IllegalArgumentException {
        mFoodItemList.add(item);
        mFoodNameItemList.add(item);
        mFoodItemNotesList.add(item);
    }

    public String[] getItems() {
        String[] items = new String[mFoodItemList.size()];
        return mFoodItemList.toArray(items);
    }

    public void clear() {
        mFoodItemList.clear();
    }

    public void addNameItem(String item) throws IllegalArgumentException {
        mFoodNameItemList.add(item);
    }

    public String[] getFoodNameItems() {
        String[] items = new String[mFoodNameItemList.size()];
        return mFoodNameItemList.toArray(items);
    }

    public void addNotesItem(String item) throws IllegalArgumentException {
        mFoodNameItemList.add(item);
    }

    public String[] getFoodItemsNotes() {
        String[] items = new String[mFoodItemNotesList.size()];
        return mFoodItemNotesList.toArray(items);
    }
}


