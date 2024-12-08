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

import com.example.mycabinet.Database.DatabaseClass;

public class MainActivity extends AppCompatActivity {

    private Kitchen kitchen = Kitchen.getInstance();
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;

    EditText foodName;
    ReminderAdapter reminderAdapter;
    DatabaseClass DatabaseClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("NEW_SECTION_NAME");
            if (name != null) {
                FoodSection section = new FoodSection(name);

                kitchen.addSection(section);

                Toast.makeText(this, "Section added: " + name, Toast.LENGTH_SHORT).show();
            }
        }

        ToggleButton toggleButton = findViewById(R.id.sort_section_button);
        toggleButton.setChecked(true);

        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SectionSort sorter = new SectionSort(kitchen.getSections());
                    sorter.sortByName();

                    loadFragment(new ListSectionView(kitchen));
                } else {
                    SectionSort sorter = new SectionSort(kitchen.getSections());
                    sorter.sortBySize();

                    loadFragment(new ListSectionView(kitchen));
                }
            }
        });

        SectionSort sorter = new SectionSort(kitchen.getSections());
        sorter.sortByName();

        loadFragment(new ListSectionView(kitchen));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
            if (searchIcon != null) {
                searchIcon.setImageResource(R.drawable.search_icon);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);

                intent.putExtra("QUERY", query);

                Log.d("SEARCH", "Sent Query: " + query);

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
        int id = item.getItemId();

        if (id == R.id.settings_button) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.app_bar_by_date) {
            Intent intent = new Intent(this, SearchActivity.class);

            intent.putExtra("QUERY", "");

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.kitchenFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void showSection(int position) {
        FoodSection section = kitchen.getSections().get(position);
    }

    public void addFoodSection(View view) {
        Intent intent = new Intent(this, AddSectionActivity.class);

        startActivity(intent);
    }
}