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
The ReminderBroadcastReceiver class
 */

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    // Override the onReceive method to handle the broadcast intent
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check for notification permission
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "reminder_channel")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle("Reminder")
                .setContentText("It's time for your scheduled task")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        int notificationId = (int) System.currentTimeMillis();

        notificationManager.notify(notificationId, builder.build());
    }
}
