package com.example.mycabinet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;

public class AddItemActivity extends AppCompatActivity {

    EditText foodName;
    EditText foodNotes;
    EditText foodMonth;
    EditText foodDay;
    EditText foodYear;
    Button addButton;
    Button cancelButton;

    int NAME_CHAR_LIMIT = 30;
    int NOTES_CHAR_LIMIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent inIntent = getIntent();
        String toSection = inIntent.getStringExtra("FROM_SECTION");

        foodName = findViewById(R.id.food_name);
        foodNotes = findViewById(R.id.food_notes);
        foodMonth = findViewById(R.id.food_month);
        foodDay = findViewById(R.id.food_day);
        foodYear = findViewById(R.id.food_year);

        cancelButton = findViewById(R.id.cancel_item_button);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SectionActivity.class);
            intent.putExtra("SECTION_TO_VIEW", toSection);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        addButton = findViewById(R.id.add_item_button);

        addButton.setOnClickListener(view -> {
            String name = foodName.getText().toString();
            String notes = foodNotes.getText().toString();
            String month_string = foodMonth.getText().toString();
            String day_string = foodDay.getText().toString();
            String year_string = foodYear.getText().toString();

            boolean isValid = true;

            if (name.isEmpty()) {
                // dialog box, enter name
                createDialog("Invalid name", "Enter a name for the food item");
                isValid = false;
            } else if (name.length() > NAME_CHAR_LIMIT) {
                // dialog box, shorten name
                createDialog("Invalid name", "Enter a shorter name for the food item (Max 30 characters)");
                isValid = false;
            } else if (notes.length() > NOTES_CHAR_LIMIT) {
                // dialog box, shorten notes
                createDialog("Invalid notes", "Enter shorter notes (Max 100 characters)");
                isValid = false;
            } else if (month_string.isEmpty() || day_string.isEmpty() || year_string.isEmpty()) {
                // dialog box, enter date
                createDialog("Invalid date", "Enter a date for the food item");
                isValid = false;
            } else {
                int month = Integer.parseInt(month_string);
                int day = Integer.parseInt(day_string);
                int year = Integer.parseInt(year_string);

                if (!isValidDate(month, day, year)) {
                    // dialog box, enter valid date
                    createDialog("Invalid date", "Enter a valid date for the food item");
                    isValid = false;
                } else {
                    LocalDate date = LocalDate.of(year, month, day);
                    if (date.isBefore(LocalDate.now())) {
                        // dialog box, enter future date
                        createDialog("Invalid date", "Enter a future date for the food item");
                        isValid = false;
                    }
                }
            }
            if (isValid) {
                Intent intent = new Intent(this, SectionActivity.class);

                intent.putExtra("NAME", name);
                intent.putExtra("NOTES", notes);
                intent.putExtra("MONTH", month_string);
                intent.putExtra("DAY", day_string);
                intent.putExtra("YEAR", year_string);

                Log.d("AddItemActivity", "Valid name given");

                intent.putExtra("SECTION_TO_VIEW", toSection);
                Log.d("AddItemActivity", "Section to view given: " + toSection);

                startActivity(intent);
            }
        });
    }

    private boolean isValidDate(int month, int day, int year) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (year < LocalDate.now().getYear() || year > 9999) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if (isShortMonth(month) && (day > 30)) {
            return false;
        }
        if (month == 2) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                return day <= 29;
            } else {
                return day <= 28;
            }
        }
        return true;
    }

    private boolean isShortMonth(int month) {
        int[] SHORT_MONTHS = {4, 6, 9, 11};

        for (int shortMonth : SHORT_MONTHS) {
            if (month == shortMonth) {
                return true;
            }
        }
        return false;
    }

    private void createDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(title)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Automatically closes window
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}