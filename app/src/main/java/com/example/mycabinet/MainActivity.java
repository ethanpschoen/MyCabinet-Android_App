package com.example.mycabinet;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String sectionName;
    private ArrayList<FoodItem> sectionItems;
    private String sectionColor;
    private ImageView sectionIcon;
    private int sectionSize;

    private Kitchen kitchen = new Kitchen();
    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;

    public MainActivity() {
//        this was food section
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//         Initialize sections
        String[] sections = {"Fruits", "Vegetables", "Grains", "Dairy", "Meats", "Pantry", "Freezer", "Refrigerator"};
        for (String sectionName : sections) {
            FoodSection section = new FoodSection(sectionName);
            for (int i = 0; i < 10; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    section.addFoodItem(new FoodItem("Item " + i, LocalDate.of(2025, 1, 1)));
                }
            }
            kitchen.addSection(section);
        }

        // Load fragment if savedInstanceState is null
        if (savedInstanceState == null) {
            loadFragment(new ListSectionView(kitchen));
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void foodItem(View view) {
        Intent i = new Intent(getApplicationContext(), FoodItem.class);
        startActivity(i);
    }


    public MainActivity(String sectionName) {
        this.sectionName = sectionName;
        this.sectionItems = new ArrayList<FoodItem>();
        this.sectionColor = "#000000";
        this.sectionIcon = null;
        this.sectionSize = 0;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<FoodItem> getSectionItems() {
        System.out.println(sectionItems.get(0).getItemName());
        System.out.println(sectionItems.toString());
        return sectionItems;
    }

    public void setSectionItems(ArrayList<FoodItem> sectionItems) {
        this.sectionItems = sectionItems;
    }

    public String getSectionColor() {
        return sectionColor;
    }

    public void setSectionColor(String sectionColor) {
        this.sectionColor = sectionColor;
    }

    public ImageView getSectionIcon() {
        return sectionIcon;
    }

    public void setSectionIcon(ImageView sectionIcon) {
        this.sectionIcon = sectionIcon;
    }

    public int getSectionSize() {
        return sectionSize;
    }

    public void setSectionSize(int sectionSize) {
        this.sectionSize = sectionSize;
    }

    public void addFoodItem(FoodItem item) {
        sectionItems.add(item);
        sectionSize++;
        System.out.println(sectionItems.get(0).getItemName());
    }

    public void removeFoodItem(FoodItem item) {
        sectionItems.remove(item);
        sectionSize--;
    }

    public void clearFoodItems() {
        sectionItems.clear();
        sectionSize = 0;
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
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
}





//
//}
//
