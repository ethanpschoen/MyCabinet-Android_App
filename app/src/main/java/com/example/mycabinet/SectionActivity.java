package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import java.time.LocalDate;

/*
The SectionActivity is an Activity that displays the items in a FoodSection, accessed via the MainActivity.
Here, the Chef can view all of the FoodItems in the selected FoodSection.
It is a scrollable list displaying the name and date of each FoodItem in the FoodSection.
As well, there is a delete button for each FoodItem and an add button at the bottom.
There are buttons to sort the FoodItems by name or date.
Finally, there is a settings button, accessible in the AppBar, as well as a back button to go back to MainActivity.
 */

public class SectionActivity extends AppCompatActivity {

    /*
    Member variables

    section: FoodSection representing the section of the kitchen
     */
    private FoodSection section;

    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;

    EditText foodName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the activity layout
        setContentView(R.layout.activity_section);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.section_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Set up the back button
        findViewById(R.id.back_button_from_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MainActivity
                Intent intent = new Intent(SectionActivity.this, MainActivity.class);
                startActivity(intent);

                // Transition animation backwards
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Set up the settings button
        findViewById(R.id.settings_button_from_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to SettingsActivity
                Intent intent = new Intent(SectionActivity.this, SettingsActivity.class);
                // Put the section name in the intent for navigating back to it
                intent.putExtra("FROM_SECTION", section.getSectionName());
                startActivity(intent);
            }
        });

        // Get the Kitchen data
        Kitchen kitchen = Kitchen.getInstance();

        // Get the data from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            // Get the section name from the Intent
            String section_name = intent.getStringExtra("SECTION_TO_VIEW");
            section = kitchen.getSection(section_name);

            // Set the title of the toolbar to the section name
            TextView toolbarTitle = findViewById(R.id.toolbar_title);
            toolbarTitle.setText(section.getSectionName());

            // Get new FoodItem data from AddItemActivity (if applicable)
            String name = intent.getStringExtra("NAME");
            String notes = intent.getStringExtra("NOTES");
            String month = intent.getStringExtra("MONTH");
            String day = intent.getStringExtra("DAY");
            String year = intent.getStringExtra("YEAR");

            // If there is new FoodItem data, add it to the section
            if (name != null) {
                // Create a new FoodItem object and add it to the section
                FoodItem item = new FoodItem(name, LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), notes);
                section.addFoodItem(item);

                // Display a toast message to confirm the item was added
                Toast.makeText(this, "Item added: " + name, Toast.LENGTH_SHORT).show();
            }
        }

        // Set up the toggle button for sorting
        ToggleButton toggleButton = findViewById(R.id.sort_item_button);
        toggleButton.setChecked(true);

        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // If the button is checked, sort by name
                    ItemSort sorter = new ItemSort(section.getSectionItems());
                    sorter.sortByName();

                    // Re-load fragment to update the view
                    loadFragment(new ListItemView(section));
                } else {
                    // If the button is unchecked, sort by date
                    ItemSort sorter = new ItemSort(section.getSectionItems());
                    sorter.sortByDate();

                    // Re-load fragment to update the view
                    loadFragment(new ListItemView(section));
                }
            }
        });

        // Sort the items by name (to start)
        ItemSort sorter = new ItemSort(section.getSectionItems());
        sorter.sortByName();

        // Load the fragment
        loadFragment(new ListItemView(section));
    }

    // Helper method to load a fragment
    private boolean loadFragment(Fragment fragment) {
        // Check if fragment is null
        if (fragment != null) {
            // Replace the current fragment with the new one
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sectionFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    // Helper method to move to AddItemActivity when "Add Item" button is clicked
    public void addFoodItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);

        // Put the section name in the intent for navigating back to it
        intent.putExtra("FROM_SECTION", section.getSectionName());

        startActivity(intent);
    }
}