package com.example.mycabinet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private Kitchen kitchen = new Kitchen();
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        Toast.makeText(this, "Menu created", Toast.LENGTH_SHORT).show();
        Log.d("Menu", "Menu created");
        System.out.println("menu inflated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings_button) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSection(int position) {
        FoodSection section = kitchen.getSections().get(position);
    }
}