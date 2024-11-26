package com.example.mycabinet;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddItemActivity extends AppCompatActivity {

    EditText foodName;
    EditText foodNotes;
    EditText foodMonth;
    EditText foodDay;
    EditText foodYear;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        foodName = findViewById(R.id.food_name);
        foodNotes = findViewById(R.id.food_notes);
        foodMonth = findViewById(R.id.food_month);
        foodDay = findViewById(R.id.food_day);
        foodYear = findViewById(R.id.food_year);
        addButton = findViewById(R.id.add_item_button);

        addButton.setOnClickListener(view -> {
            String name = foodName.getText().toString();
            String notes = foodNotes.getText().toString();
            String month = foodMonth.getText().toString();
            String day = foodDay.getText().toString();
            String year = foodYear.getText().toString();

        });
    }
}