package com.example.mycabinet;

import android.widget.Button;

import java.util.HashMap;

// Change this set-up to just use SharedPreferences?
public class Preferences {
    HashMap<String, Boolean> notifications;
    Boolean lightTheme;
    int daysBeforeReminder;

    public Preferences() {
        notifications = new HashMap<String, Boolean>();
        lightTheme = true;
        daysBeforeReminder = 3;
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
}
