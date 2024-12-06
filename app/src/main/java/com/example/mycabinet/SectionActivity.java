package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycabinet.Database.DatabaseClass;

import java.time.LocalDate;
import java.util.ArrayList;

public class SectionActivity extends AppCompatActivity {

    private Kitchen kitchen;
    private FoodSection section;
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;
    EditText foodName;
    ReminderAdapter reminderAdapter;
    DatabaseClass DatabaseClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        Toolbar toolbar = findViewById(R.id.section_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionActivity.this, MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        findViewById(R.id.settings_button_from_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionActivity.this, SettingsActivity.class);
                intent.putExtra("FROM_SECTION", section);
                startActivity(intent);
            }
        });

        Kitchen kitchen = Kitchen.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            String section_name = intent.getStringExtra("SECTION_TO_VIEW");
            section = kitchen.getSection(section_name);
            TextView toolbarTitle = findViewById(R.id.toolbar_title);
            toolbarTitle.setText(section.getSectionName());

            String name = intent.getStringExtra("NAME");
            String notes = intent.getStringExtra("NOTES");
            String month = intent.getStringExtra("MONTH");
            String day = intent.getStringExtra("DAY");
            String year = intent.getStringExtra("YEAR");

            if (name != null) {
                FoodItem item = new FoodItem(name, LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), notes);

                section.addFoodItem(item);
            }
        }
        printItems(section.getSectionItems());

        loadFragment(new ListItemView(section));
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sectionFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void showItem(int position) {
        FoodItem item = section.getSectionItems().get(position);
    }

    public void addFoodItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);

        intent.putExtra("FROM_SECTION", section.getSectionName());

        startActivity(intent);
    }

    public void printItems(ArrayList<FoodItem> items) {
        for (FoodItem item : items) {
            Log.d("SectionActivity", item.getItemName());
        }
    }
}