
package com.example.mycabinet;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycabinet.Database.DatabaseClass;
import com.example.mycabinet.Database.ReminderClass;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Kitchen kitchen = new Kitchen();
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
        settingsActivity = findViewById(R.id.settingsActivity);

        settingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("NAME");
            String notes = intent.getStringExtra("NOTES");
            int month = intent.getIntExtra("MONTH", LocalDate.now().getMonthValue());
            int day = intent.getIntExtra("DAY", LocalDate.now().getDayOfMonth());
            int year = intent.getIntExtra("YEAR", LocalDate.now().getYear());

            FoodItem item = new FoodItem(name, LocalDate.of(year, month, day));
            Toast.makeText(this, "Item received: " + item.getItemName(), Toast.LENGTH_SHORT).show();

        }


        if (savedInstanceState == null) {
            loadFragment(new ListSectionView(kitchen));
            String[] sections = {"Fruits", "Vegetables", "Grains", "Dairy", "Meats", "Pantry", "Freezer", "Refrigerator"};
            for (String sectionName : sections){
                FoodSection section = new FoodSection(sectionName);
                for (int i = 0; i < 10; i++){
                    section.addFoodItem(new FoodItem("Item " + i, LocalDate.of(2025, 1, 1)));
                }
                kitchen.addSection(section);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        setAdapter();

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
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void showSection(int position) {
        FoodSection section = kitchen.getSections().get(position);
    }

    public void addFoodItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
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