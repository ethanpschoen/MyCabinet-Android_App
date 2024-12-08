package com.example.mycabinet;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_setReminder, btn_doneReminder;
    Spinner btn_setDay;
    String timeTonotify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifs_preferences);

        btn_setReminder = (Button) findViewById(R.id.btn_setReminder);
        btn_doneReminder = (Button) findViewById(R.id.btn_doneReminder);

        btn_setDay = (Spinner) findViewById(R.id.day_select_menu);

        btn_setReminder.setOnClickListener(this);
        btn_doneReminder.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_list, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btn_setDay.setAdapter(adapter);

        btn_setDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedDay = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String selectedDay = adapterView.getItemAtPosition(0).toString();
            }
        });

        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        findViewById(R.id.back_button_from_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();

                if (intent.getStringExtra("FROM_SECTION") != null) {
                    Intent outIntent = new Intent(SettingsActivity.this, SectionActivity.class);

                    String toSection = intent.getStringExtra("FROM_SECTION");
                    outIntent.putExtra("SECTION_TO_VIEW", toSection);

                    startActivity(outIntent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else if (intent.getStringExtra("QUERY_FROM_SEARCH") != null) {
                    Intent outIntent = new Intent(SettingsActivity.this, SearchActivity.class);

                    String query = intent.getStringExtra("QUERY_FROM_SEARCH");
                    outIntent.putExtra("QUERY", query);

                    startActivity(outIntent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    Intent outIntent = new Intent(SettingsActivity.this, MainActivity.class);

                    startActivity(outIntent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

    }
    //if the user selects their reminder time, then they can submit their reminder
    @Override
    public void onClick(View v) {
        if (v == btn_setReminder) {
            selectTime();

        } else if (v == btn_doneReminder) {
            submit();
        }
    }
    //this method will alert chef and tell them if their reminder has been saved or if they need to set their time and days in advance
    private void submit() {
        if (btn_setReminder.getText().toString().equals("Set your reminder time") ||
                btn_setDay.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a date and time for your food item", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Reminder set for " + btn_setReminder.getText().toString() +
                    " for " + btn_setDay.getSelectedItem().toString() + "before expiration", Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    //method used to display time picker dialog and allows the user to select a time
    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                timeTonotify = hr + ":" + min;
                btn_setReminder.setText(timeTonotify);

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

}
