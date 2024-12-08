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

/*
The ListItemView is a Fragment that displays the items in a FoodSection.
It is accessed via a SectionActivity.
It is a scrollable list displaying the name and date of each FoodItem in the FoodSection.
As well, there is a delete button for each FoodItem.
 */

public class ListItemView extends Fragment {

    /*
    Member variables

    recyclerView: a RecyclerView object that displays the items in the section
    section: a FoodSection object that contains the items to be displayed
    adapter: a ListItemAdapter object that holds each item in the section
     */
    private RecyclerView recyclerView;
    private FoodSection section;
    private ListItemAdapter adapter;


    // Constructor to initialize the section
    public ListItemView(FoodSection section) {
        this.section = section;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_item_view, container, false);

        // Initialize member variables
        if (getActivity() instanceof SectionActivity) {
            SectionActivity activity = (SectionActivity) getActivity();

            recyclerView = view.findViewById(R.id.item_list);

            adapter = new ListItemAdapter(activity, section);

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