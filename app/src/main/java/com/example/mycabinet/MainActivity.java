package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private Kitchen kitchen;
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;
    Button settingsActivity;
    EditText foodName;
    ReminderAdapter reminderAdapter;
    DatabaseClass DatabaseClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsActivity = findViewById(R.id.sectionSettingsActivity);

        settingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("FROM", "kitchen");
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("KITCHEN_TO_VIEW")) {
                kitchen = intent.getParcelableExtra("KITCHEN_TO_VIEW");
                Toast.makeText(this, "Kitchen received", Toast.LENGTH_SHORT).show();
            }

            String name = intent.getStringExtra("NEW_SECTION_NAME");
            if (name != null) {
                FoodSection section = new FoodSection(name);
                Toast.makeText(this, "Section received: " + section.getSectionName(), Toast.LENGTH_SHORT).show();

                kitchen.addSection(section);
            }
        }

        if (kitchen == null) {
            kitchen = new Kitchen();
            String[] sections = {"Fruits", "Vegetables", "Grains", "Dairy", "Meats", "Pantry", "Freezer", "Refrigerator"};
            for (String sectionName : sections){
                FoodSection section = new FoodSection(sectionName);
                for (int i = 0; i < 10; i++){
                    section.addFoodItem(new FoodItem("Item " + i, LocalDate.of(2025, 1, 1)));
                }
                kitchen.addSection(section);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("KITCHEN", kitchen);
    }

//    private void setAdapter() {
//        List<ReminderClass> classList = databaseClass.FoodDao().getAllData();
//        reminderAdapter = new ReminderAdapter(getApplicationContext(), classList);
//        recyclerview.setAdapter(reminderAdapter);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.appbar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.settings_button:
//                Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show();
//                goToSettingsActivity(item);
//                return true;
//
//                default:
//                return super.onOptionsItemSelected(item);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

        intent.putExtra("FROM_KITCHEN", kitchen);

        startActivity(intent);
    }

//    public void onClick(View view){
//        if (view.getId() == R.id.btn_settingsActivity){
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        }
//    }
//    public void onClickListener(View v){
//
//        if (v == settingsActivity){
//            goToSettingsActivity(v);
//        }
//    }
//    public void goToSettingsActivity(View view) {
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
//    }
}