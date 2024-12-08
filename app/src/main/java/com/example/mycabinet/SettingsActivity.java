package com.example.mycabinet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mycabinet.Database.DatabaseClass;
import com.example.mycabinet.Database.ReminderClass;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    Button settingsActivity, btn_setReminder, btn_setDay, btn_doneReminder;
    String timeTonotify;
    DatabaseClass databaseClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifs_preferences);

        btn_setReminder = (Button) findViewById(R.id.btn_setReminder);
        btn_setDay = (Button) findViewById(R.id.btn_setDay);
//        settingsActivity = (Button) findViewById(R.id.sectionSettingsActivity);
        btn_doneReminder = (Button) findViewById(R.id.btn_doneReminder);

        btn_setReminder.setOnClickListener(this);
        btn_setDay.setOnClickListener(this);
//        btn_time.setOnClickListener(this);
        btn_doneReminder.setOnClickListener(this);

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
    @Override
    public void onClick(View v) {
        if (v ==btn_setReminder){
            selectTime();

        }
        else if (v == btn_setDay){
            selectDate();

        }
//        else if (v == btn_time){
//            selectTime();
//        }
        else if (v == btn_doneReminder){
            submit();
        }

    }

    private void submit(){
        if (btn_setReminder.getText().toString().equals("Set your reminder time") || btn_setDay.getText().toString().equals("Set your reminder day")){
            Toast.makeText(this, "Please select a date and time for your food item", Toast.LENGTH_SHORT).show();
    }else{
            ReminderClass reminderClass=new ReminderClass();
//            reminderClass.setFoodName(btn_setReminder.getText().toString().trim());
            reminderClass.setFoodDate(btn_setReminder.getText().toString().trim());
            reminderClass.setFoodTime(btn_setDay.getText().toString().trim());
            databaseClass.foodDao().insertAll(reminderClass);
            finish();

        }

        }

    private void selectTime() {
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify=i+":"+i1;
                btn_doneReminder.setText(timeTonotify);

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void selectDate(){
        Calendar calendar=Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btn_setDay.setText(day+"/"+(month+1)+"/"+year);

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {
        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0){
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;

        }

        if (hour == 0){
            time = "12" + ":" + formattedMinute + " AM";}
        else if (hour < 12){
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12){
            time = "12" + ":" + formattedMinute + " PM";
        }
        return time;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals("notification_type_1")) {
//            // update Preferences class
//        } else if (key.equals("notification_type_2")) {
//            // update Preferences class
//        } else if (key.equals("light_theme")) {
//            // update Preferences class
//        } else if (key.equals("reminder_days")) {
//            // update Preferences class
//        }
//    }
}