package com.example.mycabinet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
The ListItemView is a Fragment that displays the items in the Kitchen matching a search query.
It is accessed via the SearchActivity.
It is a scrollable list displaying the name and date of each FoodItem.
As well, there is a delete button for each FoodItem.
 */

public class SearchItemView extends Fragment {

    /*
    Member variables

    recyclerView: a RecyclerView object that displays the items in the section
    items: an ArrayList of FoodItem objects that contains the items to be displayed
    adapter: a SearchItemAdapter object that holds each item in the section
     */
    private RecyclerView recyclerView;
    private ArrayList<FoodItem> items;
    private SearchItemAdapter adapter;


    // Constructor to initialize the item list
    public SearchItemView(ArrayList<FoodItem> items) {
        this.items = items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item_view, container, false);

        // Initialize member variables
        if (getActivity() instanceof SearchActivity) {
            SearchActivity activity = (SearchActivity) getActivity();

            recyclerView = view.findViewById(R.id.search_list);

            adapter = new SearchItemAdapter(activity, items);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

            // Set up RecyclerView
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } else {
            throw new RuntimeException("Not attached to activity");
        }

        return view;
    }
}