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

public class AddSectionActivity extends AppCompatActivity {

    EditText sectionName;
    Button addButton;

    int NAME_CHAR_LIMIT = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);

        Log.d("AddSectionActivity", "onCreate: started");

        Intent intent = getIntent();
        Kitchen kitchen = intent.getParcelableExtra("FROM_KITCHEN");

        String[] sections = new String[kitchen.getSections().size()];
        for (int i = 0; i < kitchen.getSections().size(); i++){
            sections[i] = kitchen.getSections().get(i).getSectionName();
        }

        sectionName = findViewById(R.id.section_name);
        addButton = findViewById(R.id.add_section_button);

        addButton.setOnClickListener(view -> {
            String name = sectionName.getText().toString();

            if (name.isEmpty()) {
                createDialog("Invalid name", "Enter a name for the section");
            } else if (name.length() > NAME_CHAR_LIMIT) {
                createDialog("Invalid name", "Enter a shorter name for the section (Max 30 characters)");
            } else if (isRepeatName(name, sections)) {
                createDialog("Invalid name", "Section already exists");
            } else {
                // method to return to MainActivity with new Section
                Toast.makeText(this, "Valid section", Toast.LENGTH_SHORT).show();
                Intent outIntent = new Intent(this, MainActivity.class);

                outIntent.putExtra("NEW_SECTION_NAME", name);
                outIntent.putExtra("KITCHEN_TO_VIEW", kitchen);

                startActivity(outIntent);
            }
        });
    }

    private boolean isRepeatName(String name, String[] sections) {
        for (String section : sections) {
            if (section.equals(name)) {
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