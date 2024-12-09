package com.example.mycabinet;

import android.widget.Button;

import java.util.Calendar;
import java.util.HashMap;

// Change this set-up to just use SharedPreferences?
public class Preferences {
    HashMap<String, Boolean> notifications;
    Boolean lightTheme;
    int daysBeforeReminder;
    Calendar timeOfDayOfReminder;

    public Preferences() {
        notifications = new HashMap<String, Boolean>();
        lightTheme = true;
        daysBeforeReminder = 3;
        timeOfDayOfReminder = Calendar.getInstance();
        timeOfDayOfReminder.set(Calendar.HOUR_OF_DAY, 9);
        timeOfDayOfReminder.set(Calendar.MINUTE, 0);
        timeOfDayOfReminder.set(Calendar.SECOND, 0);
    }

    public HashMap<String, Boolean> getNotifications() {
        return notifications;
    }
    // getters and setters for notifications and lightTheme
    public void setNotifications(HashMap<String, Boolean> notifications) {
        this.notifications = notifications;
    }

    public Boolean getLightTheme() {
        return lightTheme;
    }

    public void setLightTheme(Boolean lightTheme) {
        this.lightTheme = lightTheme;
    }

    public int getDaysBeforeReminder() {
        return daysBeforeReminder;
    }

    public void setDaysBeforeReminder(int daysBeforeReminder) {
        this.daysBeforeReminder = daysBeforeReminder;
    }

    public Calendar getTimeOfDayOfReminder() {
        return timeOfDayOfReminder;
    }

    public void setTimeOfDayOfReminder(Calendar timeOfDayOfReminder) {
        this.timeOfDayOfReminder = timeOfDayOfReminder;
    }
}
