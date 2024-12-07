package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycabinet.Database.DatabaseClass;

import java.time.LocalDate;

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

        if (kitchen.getSections().isEmpty()) {
            String[] sections = {"Fruits", "Vegetables", "Grains", "Dairy", "Meats", "Pantry", "Freezer", "Refrigerator"};
            int count = 0;
            for (String sectionName : sections){
                FoodSection section = new FoodSection(sectionName);
                for (int i = 0; i < 10 - count; i++){
                    section.addFoodItem(new FoodItem("Item " + i, LocalDate.of(2025, 1, 1)));
                }
                kitchen.addSection(section);
                count += 1;
            }
        }

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("NEW_SECTION_NAME");
            if (name != null) {
                FoodSection section = new FoodSection(name);

                kitchen.addSection(section);

                Toast.makeText(this, "Section added: " + name, Toast.LENGTH_SHORT).show();
            }
        }

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings_button) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
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