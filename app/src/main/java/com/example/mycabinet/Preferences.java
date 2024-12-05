package com.example.mycabinet;

import android.widget.Button;

import java.util.HashMap;

public class Preferences {
    Button btn_setReminder, btn_setDay, btn_doneReminder;
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
