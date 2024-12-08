package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


/*
The MainActivity is the core Activity of the app.
Here, the Chef can view all of the FoodSections in the Kitchen.
It is a scrollable list displaying the name and size of each FoodSection.
They can also add a new FoodSection to the Kitchen, or delete a FoodSection.
There are buttons to sort the FoodSections by name or size.
Finally, there are settings, search, and items-by-date buttons, all accessible in the AppBar.
 */

public class MainActivity extends AppCompatActivity {

    /*
    Member variables

    kitchen: A singleton instance of the Kitchen class, containing all of the data used in the app
     */
    private Kitchen kitchen = Kitchen.getInstance();

    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;

    EditText foodName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the activity layout
        setContentView(R.layout.activity_main);

        // Receive intent from AddSectionActivity, which has the name of the new section to be added
        Intent intent = getIntent();
        if (intent != null) {
            // Get the name of the new section
            String name = intent.getStringExtra("NEW_SECTION_NAME");
            if (name != null) {
                // Create a new FoodSection object and add it to the kitchen
                FoodSection section = new FoodSection(name);
                kitchen.addSection(section);

                // Display a toast message to confirm the section was added
                Toast.makeText(this, "Section added: " + name, Toast.LENGTH_SHORT).show();
            }
        }

        // Set up the toggle button for sorting
        ToggleButton toggleButton = findViewById(R.id.sort_section_button);
        toggleButton.setChecked(true);

        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // If the button is checked, sort by name
                    SectionSort sorter = new SectionSort(kitchen.getSections());
                    sorter.sortByName();

                    // Re-load fragment to update the view
                    loadFragment(new ListSectionView(kitchen));
                } else {
                    // If the button is unchecked, sort by size
                    SectionSort sorter = new SectionSort(kitchen.getSections());
                    sorter.sortBySize();

                    // Re-load fragment to update the view
                    loadFragment(new ListSectionView(kitchen));
                }
            }
        });


        // Sort the sections by name (to start)
        SectionSort sorter = new SectionSort(kitchen.getSections());
        sorter.sortByName();

        // Load the fragment
        loadFragment(new ListSectionView(kitchen));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the AppBar menu
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        // Set up the search bar
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Change the icon (SearchView icon could not be changed in XML for some reason, we spent way too much time trying to figure this out)
        if (searchView != null) {
            ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
            if (searchIcon != null) {
                // Change image to correct png (instead of default by Android)
                searchIcon.setImageResource(R.drawable.search_icon);
            }
        }

        // Set up the search query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // When Chef submits a query, send it to SearchActivity
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);

                // Put the query in the intent
                intent.putExtra("QUERY", query);

                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.settings_button) {
            // Go to SettingsActivity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.app_bar_by_date) {
            // Go to SearchActivity, where it sorts qualified items by date
            Intent intent = new Intent(this, SearchActivity.class);

            // With empty query, it will sort all items by date
            intent.putExtra("QUERY", "");

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // Helper method to load a fragment
    private boolean loadFragment(Fragment fragment) {
        // Check if fragment is null
        if (fragment != null) {
            // Replace the current fragment with the new one
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.kitchenFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    // Helper method to move to AddSectionActivity when "Add Section" button is clicked
    public void addFoodSection(View view) {
        Intent intent = new Intent(this, AddSectionActivity.class);

        startActivity(intent);
    }
}