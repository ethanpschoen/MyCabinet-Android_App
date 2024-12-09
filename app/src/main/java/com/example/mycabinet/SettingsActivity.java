package com.example.mycabinet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/*

 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    Member variables
    btn_setReminder: button to set the reminder time
    btn_doneReminder: button to confirm the reminder time
    btn_setDay: spinner to select the day before expiration date to send a reminder
    timeTonotify: the time to notify the user to take the food item
    kitchen: a singleton instance of the Kitchen class, containing all of the data used in the app
     */

    Button btn_setReminder, btn_doneReminder;
    Spinner btn_setDay;
    String timeTonotify;

    Kitchen kitchen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifs_preferences);

        kitchen = Kitchen.getInstance();

        btn_setReminder = (Button) findViewById(R.id.btn_setReminder);
        btn_doneReminder = (Button) findViewById(R.id.btn_doneReminder);

        btn_setDay = (Spinner) findViewById(R.id.day_select_menu);

        btn_setReminder.setOnClickListener(this);
        btn_doneReminder.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_list, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btn_setDay.setAdapter(adapter);

        // Checks for what the Chef selects from the Spinner for the day before expiration
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

        // This is the back button to go back to the previous activity
        findViewById(R.id.back_button_from_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();

                if (intent.getStringExtra("FROM_SECTION") != null) {
                    Intent outIntent = new Intent(SettingsActivity.this, SectionActivity.class);

                    String toSection = intent.getStringExtra("FROM_SECTION");
                    outIntent.putExtra("SECTION_TO_VIEW", toSection);

                    setResult(RESULT_OK);

                    updateReminders(SettingsActivity.this);

                    startActivity(outIntent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else if (intent.getStringExtra("QUERY_FROM_SEARCH") != null) {
                    Intent outIntent = new Intent(SettingsActivity.this, SearchActivity.class);

                    String query = intent.getStringExtra("QUERY_FROM_SEARCH");
                    outIntent.putExtra("QUERY", query);

                    setResult(RESULT_OK);

                    updateReminders(SettingsActivity.this);

                    startActivity(outIntent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    Intent outIntent = new Intent(SettingsActivity.this, MainActivity.class);

                    updateReminders(SettingsActivity.this);

                    startActivity(outIntent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

    }

    // Checks for which Button is clicked and calls the appropriate method
    @Override
    public void onClick(View v) {
        if (v == btn_setReminder){
            selectTime();

        }

        else if (v == btn_doneReminder){
            submit();
        }
    }

    // Checks for what the Chef selects from the Spinner for the day before expiration
    private void submit(){
        if (btn_setReminder.getText().toString().equals("Set your reminder time") ||
                btn_setDay.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a date and time for your food item", Toast.LENGTH_SHORT).show();
        } else {
            Kitchen kitchen = Kitchen.getInstance();

            int daysBefore = Integer.parseInt(btn_setDay.getSelectedItem().toString().substring(0, 1));
            kitchen.getSettings().setDaysBeforeReminder(daysBefore);

            int hour = Integer.parseInt(timeTonotify.substring(0, 2));
            int minute = Integer.parseInt(timeTonotify.substring(3,5));

            Calendar timeOfDay = Calendar.getInstance();
            timeOfDay.set(Calendar.HOUR_OF_DAY, hour);
            timeOfDay.set(Calendar.MINUTE, minute);
            timeOfDay.set(Calendar.SECOND, 0);

            kitchen.getSettings().setTimeOfDayOfReminder(timeOfDay);

            updateReminders(SettingsActivity.this);

            Toast.makeText(this, "Reminder set for " + btn_setReminder.getText().toString() +
                    " for " + btn_setDay.getSelectedItem().toString().toLowerCase() + " before expiration", Toast.LENGTH_SHORT).show();

            finish();
        }

    }

    // Checks for what time the chef selects for the reminder
    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                if (min < 10) {
                    timeTonotify = hr + ":0" + min;
                } else {
                    timeTonotify = hr + ":" + min;
                }
                btn_setReminder.setText(timeTonotify);

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }


    // Method to schedule a reminder for a FoodItem
    public void scheduleReminder(Context context, FoodItem item) {
        // Get the expiration date
        LocalDate expirationDate = item.getExpirationDate();

        // Calculate the reminder date based on expiration date
        int remindDaysBefore = kitchen.getSettings().getDaysBeforeReminder();
        LocalDate reminderDate = expirationDate.minusDays(remindDaysBefore);

        // Create Calendar date
        Calendar calendar = Calendar.getInstance();
        // Set the calendar to the reminder date
        calendar.setTime(Date.from(reminderDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        // Set the time of the reminder
        calendar.set(Calendar.HOUR_OF_DAY, kitchen.getSettings().getTimeOfDayOfReminder().get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, kitchen.getSettings().getTimeOfDayOfReminder().get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, kitchen.getSettings().getTimeOfDayOfReminder().get(Calendar.SECOND));

        // Get the time in milliseconds
        long reminderTime = calendar.getTimeInMillis();


        // Get manager and intent for the reminder
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);

        // Put item name and expiration date in intent
        intent.putExtra("ITEM_NAME", item.getItemName());
        intent.putExtra("EXPIRATION_DATE", item.getExpirationDate().toString());

        // Set pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the reminder
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
        }
    }

    // Method to cancel a reminder for a FoodItem
    public void cancelReminder(Context context, FoodItem item) {
        // Get manager and intent for the reminder
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);

        // Set pending intent using unique ID for the item
        int requestCode = item.getItemID().hashCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Cancel the reminder
        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    // Method to update the reminders
    private void updateReminders(Context context) {
        cancelAllReminders(context);  // Cancel existing reminders

        // Get the updated reminder time and calculate the correct reminder times for each item
        for (FoodItem item : kitchen.getItems()) {
            scheduleReminder(context, item);  // Schedule the reminder
        }
    }

    // Method to cancel all reminders
    private void cancelAllReminders(Context context) {
        for (FoodItem item : kitchen.getItems()) {
            cancelReminder(context, item);
        }
    }
}