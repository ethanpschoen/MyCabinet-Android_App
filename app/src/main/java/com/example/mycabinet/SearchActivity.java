package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        if (intent != null) {
            query = intent.getStringExtra("QUERY");
        }

        findViewById(R.id.back_button_from_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        findViewById(R.id.settings_button_from_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);

                startActivity(intent);
            }
        });

        Kitchen kitchen = Kitchen.getInstance();

        ArrayList<FoodItem> items = new ArrayList<>();

        for (FoodSection section : kitchen.getSections()) {
            for (FoodItem item : section.getSectionItems()) {
                if (item.getItemName().toLowerCase().startsWith(query.toLowerCase())) {
                    items.add(item);
                }
            }
        }

        ItemSort sorter = new ItemSort(items);
        sorter.sortByDate();

        loadFragment(new SearchItemView(items));
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.searchFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}