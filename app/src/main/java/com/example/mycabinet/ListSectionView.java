package com.example.mycabinet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
The ListSectionView is a Fragment only accessed via MainActivity.
It displays the list of FoodSections using a RecyclerView.
It is scrollable vertically and displays the name and size of each FoodSection.
As well, there is a delete button for each FoodItem.
 */

public class ListSectionView extends Fragment {

    /*
    Member variables

    recyclerView: a RecyclerView object that holds the list of FoodSections
    kitchen: a Kitchen object that holds the list of FoodSections
    adapter: a ListSectionAdapter object that manages the list of FoodSections
     */
    private RecyclerView recyclerView;
    private Kitchen kitchen;
    private ListSectionAdapter adapter;

    // Constructor to initialize the kitchen
    public ListSectionView(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_section_view, container, false);

        // Initialize the RecyclerView and adapter
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();

            recyclerView = view.findViewById(R.id.section_list);
            adapter = new ListSectionAdapter(activity, kitchen.getSections());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

            // Set up the RecyclerView
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        } else {
            throw new RuntimeException("Not attached to activity");
        }

        return view;
    }
}