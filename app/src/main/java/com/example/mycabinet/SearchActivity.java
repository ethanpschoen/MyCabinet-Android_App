package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/*
The SearchActivity is used to search for FoodItems in the Kitchen.
Given a search query, it will find all items beginning with that query.
The Chef can click the back button to go back to MainActivity, or the settings button to go to SettingsActivity.
Additionally, they can click the delete button to remove the FoodItem from the Kitchen.
 */

public class SearchActivity extends AppCompatActivity {

    /*
    Member variable

    query: String containing the search query for which to find fitting items
     */
    private String query = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up layout view
        setContentView(R.layout.activity_search);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Get query from MainActivity
        Intent intent = getIntent();
        if (intent != null) {
            query = intent.getStringExtra("QUERY");
        }

        TextView toolbarTitle = findViewById(R.id.search_title);
        if (query.isEmpty()) {
            toolbarTitle.setText("All Items (By Date)");
        } else {
            toolbarTitle.setText("Search Results: " + query);
        }

        // Set up the back button
        findViewById(R.id.back_button_from_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MainActivity
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);

                // Transition animation backwards
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Set up the settings button
        findViewById(R.id.settings_button_from_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to SettingsActivity
                Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);

                // Put the query in the intent
                intent.putExtra("QUERY_FROM_SEARCH", query);

                startActivity(intent);
            }
        });

        // Get the data
        Kitchen kitchen = Kitchen.getInstance();

        // New ArrayList to hold the items that match the query
        ArrayList<FoodItem> items = new ArrayList<>();

        // For each FoodSection in the Kitchen
        for (FoodSection section : kitchen.getSections()) {
            // For each FoodItem in the FoodSection
            for (FoodItem item : section.getSectionItems()) {
                // If the FoodItem's name starts with the query, add it to the list
                if (item.getItemName().toLowerCase().startsWith(query.toLowerCase())) {
                    items.add(item);
                }
            }
        }

        // Sort the items by date
        ItemSort sorter = new ItemSort(items);
        sorter.sortByDate();

        // Load the fragment
        loadFragment(new SearchItemView(items));
    }


    // Helper method to load a fragment
    private boolean loadFragment(Fragment fragment) {
        // If the fragment is not null, load it
        if (fragment != null) {
            // Replace the fragment in the container view
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.searchFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}