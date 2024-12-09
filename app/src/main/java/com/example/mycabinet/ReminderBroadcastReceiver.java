package com.example.mycabinet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/*
The ReminderBroadcastReceiver class is responsible for handling the broadcast intent sent by the AlarmManager.
It creates and sends a notification to the user with the item name and expiration date.
 */

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    // Override the onReceive method to handle the broadcast intent
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the item name and expiration date from the intent
        String itemName = intent.getStringExtra("ITEM_NAME");
        String expirationDate = intent.getStringExtra("EXPIRATION_DATE");

        String month = expirationDate.substring(5, 7);
        String day = expirationDate.substring(8, 10);

        // Format the notification message
        String message = "Your " + itemName + " is about to expire on " + month + "/" + day + "!";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check for notification permission
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Create and send the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "reminder_channel")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Get the notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        int notificationId = (int) System.currentTimeMillis();

        // Send the notification
        notificationManager.notify(notificationId, builder.build());
    }
}
