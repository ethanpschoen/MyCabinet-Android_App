package com.example.mycabinet;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

    public class FoodItemButton extends Fragment {

        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState
        ) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.food_item, container, false);
        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(FoodItemButton.this)
                            .navigate(R.id.action_food_item_to_sectionlistitem);
                }
            });
        }
    }

}



