package com.example.mycabinet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

/*
The AddItemActivity is an Activity only accessed via a SectionActivity.
Here, the Chef can add a FoodItem to the FoodSection they were viewing in the SectionActivity.
They will have several EditText boxes, where they must enter the information for the FoodItem.
If the Chef enters a name and a valid date, the FoodItem information gets passed back to SectionActivity.
 */

public class AddItemActivity extends AppCompatActivity {

    /*
    Member variables

    foodName: an EditText object where Chef must enter the name of the food item
    foodNotes: an EditText object where Chef can enter any notes about the food item
    foodMonth: an EditText object where Chef must enter the month of the food item
    foodDay: an EditText object where Chef must enter the day of the food item
    foodYear: an EditText object where Chef must enter the year of the food item
    addButton: a Button object that adds the food item to the list (if valid)
    cancelButton: a Button object that cancels the add item process, returning to the item view
     */
    EditText foodName;
    EditText foodNotes;
    EditText foodMonth;
    EditText foodDay;
    EditText foodYear;
    Button addButton;
    Button cancelButton;

    // Character limits that Chef must abide by
    int NAME_CHAR_LIMIT = 30;
    int NOTES_CHAR_LIMIT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Open corresponding XML layout file
        setContentView(R.layout.activity_add_item);

        // Receive intent from SectionActivity, which has the name of the FoodSection it is coming from
        Intent inIntent = getIntent();
        String toSection = inIntent.getStringExtra("FROM_SECTION");


        // Initialize member variables
        foodName = findViewById(R.id.food_name);
        foodNotes = findViewById(R.id.food_notes);
        foodMonth = findViewById(R.id.food_month);
        foodDay = findViewById(R.id.food_day);
        foodYear = findViewById(R.id.food_year);

        // Set up cancel button
        cancelButton = findViewById(R.id.cancel_item_button);

        cancelButton.setOnClickListener(view -> {
            // Return to SectionActivity if "Cancel" button clicked
            Intent intent = new Intent(this, SectionActivity.class);
            // Put as extra the name of the FoodSection to return to
            intent.putExtra("SECTION_TO_VIEW", toSection);
            startActivity(intent);

            // Add back animation
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        // Set up add button
        addButton = findViewById(R.id.add_item_button);

        addButton.setOnClickListener(view -> {
            // Get values from EditText objects
            String name = foodName.getText().toString();
            String notes = foodNotes.getText().toString();
            String month_string = foodMonth.getText().toString();
            String day_string = foodDay.getText().toString();
            String year_string = foodYear.getText().toString();


            // Check if values are valid
            boolean isValid = true;

            if (name.isEmpty()) {
                // Dialog box, enter name
                createDialog("Invalid name", "Enter a name for the food item");
                isValid = false;
            } else if (name.length() > NAME_CHAR_LIMIT) {
                // Dialog box, shorten name
                createDialog("Invalid name", "Enter a shorter name for the food item (Max 30 characters)");
                isValid = false;
            } else if (notes.length() > NOTES_CHAR_LIMIT) {
                // Dialog box, shorten notes
                createDialog("Invalid notes", "Enter shorter notes (Max 100 characters)");
                isValid = false;
            } else if (month_string.isEmpty() || day_string.isEmpty() || year_string.isEmpty()) {
                // Dialog box, enter date
                createDialog("Invalid date", "Enter a date for the food item");
                isValid = false;
            } else {
                // Convert to integers for comparisons
                int month = Integer.parseInt(month_string);
                int day = Integer.parseInt(day_string);
                int year = Integer.parseInt(year_string);

                if (!isValidDate(month, day, year)) {
                    // Dialog box, enter valid date
                    createDialog("Invalid date", "Enter a valid date for the food item");
                    isValid = false;
                } else {
                    LocalDate date = LocalDate.of(year, month, day);
                    if (date.isBefore(LocalDate.now())) {
                        // Dialog box, enter future date
                        createDialog("Invalid date", "Enter a future date for the food item");
                        isValid = false;
                    }
                }
            }

            // If all tests pass to confirm the information given is valid
            if (isValid) {
                // Send values to SectionActivity
                Intent intent = new Intent(this, SectionActivity.class);

                intent.putExtra("NAME", name);
                intent.putExtra("NOTES", notes);
                intent.putExtra("MONTH", month_string);
                intent.putExtra("DAY", day_string);
                intent.putExtra("YEAR", year_string);

                intent.putExtra("SECTION_TO_VIEW", toSection);

                startActivity(intent);
            }
        });
    }


    /*
    boolean isValidDate(int month, int day, int year)

    Takes in three integers, confirms they are valid dates, and returns a boolean
     */
    private boolean isValidDate(int month, int day, int year) {
        // Within months
        if (month < 1 || month > 12) {
            return false;
        }
        // After this year, 4 digit number
        if (year < LocalDate.now().getYear() || year > 9999) {
            return false;
        }
        // Within days (general)
        if (day < 1 || day > 31) {
            return false;
        }
        // Within 30 days for short months
        if (isShortMonth(month) && (day > 30)) {
            return false;
        }
        // Special cases for February
        if (month == 2) {
            // Check leap year case
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                return day <= 29;
            } else {
                // Not a leap year
                return day <= 28;
            }
        }
        return true;
    }

    /*
    boolean isShortMonth(int month)

    Takes in an integer, confirms it is a short month, and returns a boolean
     */
    private boolean isShortMonth(int month) {
        int[] SHORT_MONTHS = {4, 6, 9, 11};

        for (int shortMonth : SHORT_MONTHS) {
            if (month == shortMonth) {
                return true;
            }
        }
        return false;
    }

    /*
    void createDialog(String title, String message)

    Takes in a title and message for the dialog box, generates the dialog box, and displays it
     */
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