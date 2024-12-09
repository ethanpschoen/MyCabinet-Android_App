package com.example.mycabinet;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
The MainActivity is the core Activity of the app.
Here, the Chef can view all of the FoodSections in the Kitchen.
It is a scrollable list displaying the name and size of each FoodSection.
They can also add a new FoodSection to the Kitchen, or delete a FoodSection.
There are buttons to sort the FoodSections by name or size.
Finally, there are settings, search, and items-by-date buttons, all accessible in the AppBar.
 */

public class MainActivity extends AppCompatActivity implements ReminderListener {

    /*
    Member variables

    kitchen: A singleton instance of the Kitchen class, containing all of the data used in the app
     */
    private Kitchen kitchen = Kitchen.getInstance();

    private RecyclerView recyclerView;
    private ListSectionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the activity layout
        setContentView(R.layout.activity_main);

        kitchen.setReminderListener(this);

        // Receive intent from AddSectionActivity, which has the name of the new section to be added
        Intent intent = getIntent();
        if (intent != null) {
            // Get the name of the new section
            String name = intent.getStringExtra("NEW_SECTION_NAME");
            if (name != null) {
                // Create a new FoodSection object and add it to the kitchen
                FoodSection section = new FoodSection(name);
                kitchen.addSection(section);

                // Display a toast message to confirm the section was added
                Toast.makeText(this, "Section added: " + name, Toast.LENGTH_SHORT).show();
            }
        }

        // Set up the toggle button for sorting
        ToggleButton toggleButton = findViewById(R.id.sort_section_button);
        toggleButton.setChecked(true);

        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // If the button is checked, sort by name
                    SectionSort sorter = new SectionSort(kitchen.getSections());
                    sorter.sortByName();

                    // Re-load fragment to update the view
                    loadFragment(new ListSectionView(kitchen));
                } else {
                    // If the button is unchecked, sort by size
                    SectionSort sorter = new SectionSort(kitchen.getSections());
                    sorter.sortBySize();

                    // Re-load fragment to update the view
                    loadFragment(new ListSectionView(kitchen));
                }
            }
        });


        // Sort the sections by name (to start)
        SectionSort sorter = new SectionSort(kitchen.getSections());
        sorter.sortByName();

        createNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent settingsIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(settingsIntent);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1);  // Request code can be any integer
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Required")
                        .setMessage("To receive reminders, please enable notifications in settings.")
                        .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        }

        setReminders(this, kitchen.getItems());

        // Load the fragment
        loadFragment(new ListSectionView(kitchen));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the AppBar menu
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        // Set up the search bar
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Change the icon (SearchView icon could not be changed in XML for some reason, we spent way too much time trying to figure this out)
        if (searchView != null) {
            ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
            if (searchIcon != null) {
                // Change image to correct png (instead of default by Android)
                searchIcon.setImageResource(R.drawable.search_icon);
            }
        }

        // Set up the search query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // When Chef submits a query, send it to SearchActivity
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);

                // Put the query in the intent
                intent.putExtra("QUERY", query);

                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.settings_button) {
            // Go to SettingsActivity
            Intent intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);

            return true;
        } else if (id == R.id.app_bar_by_date) {
            // Go to SearchActivity, where it sorts qualified items by date
            Intent intent = new Intent(this, SearchActivity.class);

            // With empty query, it will sort all items by date
            intent.putExtra("QUERY", "");

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // Helper method to load a fragment
    private boolean loadFragment(Fragment fragment) {
        // Check if fragment is null
        if (fragment != null) {
            // Replace the current fragment with the new one
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.kitchenFragmentContainerView, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    // Helper method to move to AddSectionActivity when "Add Section" button is clicked
    public void addFoodSection(View view) {
        Intent intent = new Intent(this, AddSectionActivity.class);

        startActivity(intent);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "reminder_channel";
            CharSequence name = "Reminder Notifications";
            String description = "Channel for reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void scheduleReminder(Context context, long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }

    public void cancelReminder(Context context, FoodItem item) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);

        int requestCode = item.getItemID().hashCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Log.d("Reminder", "Reminder canceled");
        }
    }

    public void cancelAllReminders(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (FoodItem item : kitchen.getItems()) {
            cancelReminder(context, item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your notification code
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message or handle it
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setReminders(Context context, ArrayList<FoodItem> items) {
        // Clear all existing reminders
        cancelAllReminders(context);

        for (FoodItem item : items) {
            LocalDate expirationDate = item.getExpirationDate();
            Log.d("Reminder", "Food item: " + item.getItemName() + ", Expiration date: " + expirationDate.toString());
            int remindDaysBefore = kitchen.getSettings().getDaysBeforeReminder();
            LocalDate reminderDate = expirationDate.minusDays(remindDaysBefore);
            Log.d("Reminder", "Reminder date: " + reminderDate.toString());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(reminderDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            calendar.set(Calendar.HOUR_OF_DAY, kitchen.getSettings().getTimeOfDayOfReminder().get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, kitchen.getSettings().getTimeOfDayOfReminder().get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, kitchen.getSettings().getTimeOfDayOfReminder().get(Calendar.SECOND));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(calendar.getTime());
            Log.d("Reminder", "Formatted date: " + formattedDate);

            long reminderTime = calendar.getTimeInMillis();

            scheduleReminder(this, reminderTime);
        }
    }

    @Override
    public void onFoodItemAdded(FoodItem item) {
        long reminderTime = calculateReminderTime(item);
        scheduleReminder(this, reminderTime);
    }

    @Override
    public void onFoodItemRemoved(FoodItem item) {
        cancelReminder(this, item);
    }

    @Override
    public void onSettingsChanged() {
        cancelAllReminders(this);
        for (FoodItem item : kitchen.getItems()) {
            long reminderTime = calculateReminderTime(item);
            scheduleReminder(this, reminderTime);
        }
    }

    private long calculateReminderTime(FoodItem item) {
        LocalDate expirationDate = item.getExpirationDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return calendar.getTimeInMillis();
    }
}
