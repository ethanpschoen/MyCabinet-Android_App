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

/*
The AddItemActivity is an Activity only accessed via MainActivity.
Here, the Chef can add a FoodSection to the Kitchen.
They will have one EditText box, where they must enter name for the FoodSection..
If the Chef enters a unique name, the FoodSection information gets passed back to MainActivity.
 */

public class AddSectionActivity extends AppCompatActivity {

    /*
    Member variables

    sectionName: an EditText object where Chef must enter the name of the section
    addButton: a Button object that adds the section to the kitchen (if valid)
    cancelButton: a Button object that cancels the add section process, returning to the section view
     */
    EditText sectionName;
    Button addButton;
    Button cancelButton;

    // Character limit that Chef must abide by
    int NAME_CHAR_LIMIT = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Open corresponding XML layout file
        setContentView(R.layout.activity_add_section);

        // Access kitchen data
        Kitchen kitchen = Kitchen.getInstance();

        // Find all section names already made by Chef
        String[] sections = new String[kitchen.getSections().size()];
        for (int i = 0; i < kitchen.getSections().size(); i++){
            sections[i] = kitchen.getSections().get(i).getSectionName();
        }


        // Initialize member variables
        sectionName = findViewById(R.id.section_name);

        // Set up cancel button
        cancelButton = findViewById(R.id.cancel_section_button);

        cancelButton.setOnClickListener(view -> {
            Intent outIntent = new Intent(this, MainActivity.class);
            startActivity(outIntent);

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        // Set up add button
        addButton = findViewById(R.id.add_section_button);

        addButton.setOnClickListener(view -> {
            // Get value from EditText object
            String name = sectionName.getText().toString();

            if (name.isEmpty()) {
                // Dialog box, enter name
                createDialog("Invalid name", "Enter a name for the section");
            } else if (name.length() > NAME_CHAR_LIMIT) {
                // Dialog box, shorten name
                createDialog("Invalid name", "Enter a shorter name for the section (Max 30 characters)");
            } else if (isRepeatName(name, sections)) {
                // Dialog box, section already exists (must be unique)
                createDialog("Invalid name", "Section already exists");
            } else {
                // If all tests pass, send name to MainActivity
                Intent outIntent = new Intent(this, MainActivity.class);

                outIntent.putExtra("NEW_SECTION_NAME", name);

                startActivity(outIntent);
            }
        });
    }


    /*
    boolean isRepeatName(String name, String[] sections)

    Takes in a name and a list of section names. Goes through each existing section
    and returns true if the name is already in the list
     */
    private boolean isRepeatName(String name, String[] sections) {
        for (String section : sections) {
            if (section.equals(name)) {
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