package com.example.mycabinet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

        sectionName = findViewById(R.id.section_name);
        addButton = findViewById(R.id.add_section_button);

        addButton.setOnClickListener(view -> {
            String name = sectionName.getText().toString();

            if (name.isEmpty()) {
                createDialog("Invalid name", "Enter a name for the section");
            } else if (name.length() > NAME_CHAR_LIMIT) {
                createDialog("Invalid name", "Enter a shorter name for the section (Max 30 characters)");
            } else {
                // method to return to MainActivity with new Section
                Toast.makeText(this, "Valid section", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);

                intent.putExtra("NAME", name);

                startActivity(intent);
            }
        });
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