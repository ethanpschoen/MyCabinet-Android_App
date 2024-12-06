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

public class SectionActivity extends AppCompatActivity {

    private FoodSection section;
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;
    Button settingsActivity;
    EditText foodName;
    ReminderAdapter reminderAdapter;
    DatabaseClass DatabaseClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Toast.makeText(this, "Section Activity", Toast.LENGTH_SHORT).show();
//        Log.d("SectionActivity", "onCreate: Section Activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        settingsActivity = findViewById(R.id.sectionSettingsActivity);

        settingsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectionActivity.this, SettingsActivity.class);
                intent.putExtra("FROM", "section");
                startActivity(intent);
            }
        });

//        if (savedInstanceState != null) {
//            section = savedInstanceState.getParcelable("KEEP_SECTION");
//            Toast.makeText(this, "Section restored: " + section.getSectionName(), Toast.LENGTH_SHORT).show();
//            Log.d("SectionActivity", "onCreate: Section restored: " + section.getSectionName());
//        }
//        else {
//            Toast.makeText(this, "Section not restored", Toast.LENGTH_SHORT).show();
//        }

        Intent intent = getIntent();
        Log.d("SectionActivity", "onCreate: Intent received");
        if (intent != null) {
            Log.d("SectionActivity", "onCreate: Intent not null");
            if (intent.hasExtra("SECTION_TO_VIEW")) {
                section = intent.getParcelableExtra("SECTION_TO_VIEW");
                Toast.makeText(this, "Section received: " + section.getSectionName(), Toast.LENGTH_SHORT).show();
                Log.d("SectionActivity", "onCreate: Section received: " + section.getSectionName());
            }

            String name = intent.getStringExtra("NAME");
            String notes = intent.getStringExtra("NOTES");
            int month = intent.getIntExtra("MONTH", LocalDate.now().getMonthValue());
            int day = intent.getIntExtra("DAY", LocalDate.now().getDayOfMonth());
            int year = intent.getIntExtra("YEAR", LocalDate.now().getYear());

            if (name != null && notes != null && month != 0 && day != 0 && year != 0) {
                FoodItem item = new FoodItem(name, LocalDate.of(year, month, day), notes);
                Toast.makeText(this, "Item received: " + item.getItemName(), Toast.LENGTH_SHORT).show();

                section.addFoodItem(item);
            }
        }
        if (intent == null) {
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
            Log.d("SectionActivity", "onCreate: Intent is null");
        }

        loadFragment(new ListItemView(section));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setAdapter();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("KEEP_SECTION", section);
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

        intent.putExtra("FROM_SECTION", section);

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