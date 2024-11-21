package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    ImageView settingsButton;

    private Kitchen kitchen = new Kitchen();
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new IconSectionView())
//                    .commit();
//        }

        kitchen.addSection(new FoodSection("Fruits"));
        kitchen.addSection(new FoodSection("Vegetables"));
        kitchen.addSection(new FoodSection("Grains"));
        kitchen.addSection(new FoodSection("Dairy"));
        kitchen.addSection(new FoodSection("Meats"));
        kitchen.addSection(new FoodSection("Pantry"));
        kitchen.addSection(new FoodSection("Freezer"));
        kitchen.addSection(new FoodSection("Refrigerator"));
//        kitchen.addSection(new FoodSection("Filler1"));
//        kitchen.addSection(new FoodSection("Filler2"));
//        kitchen.addSection(new FoodSection("Filler3"));
//        kitchen.addSection(new FoodSection("Filler4"));
//        kitchen.addSection(new FoodSection("Filler5"));
//        kitchen.addSection(new FoodSection("Filler6"));
//        kitchen.addSection(new FoodSection("Other"));

        recyclerView = findViewById(R.id.section_list);
        adapter = new ListSectionAdapter(this, kitchen.getSections());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void showSection(int position) {
        FoodSection section = kitchen.getSections().get(position);
    }
}