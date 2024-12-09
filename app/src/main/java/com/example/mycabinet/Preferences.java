package com.example.mycabinet;

import java.util.Calendar;
import java.util.HashMap;

/*
The Preferences model class represents the user's preferences in the application.
The Preferences object holds all settings for the Chef, including notifications, theme, and reminder settings.
 */

public class Preferences {

    /*
    Member variables

    notifications: HashMap of notifications for each section
    lightTheme: boolean for if the theme is light or dark
    daysBeforeReminder: int for how many days before the expiration date the reminder should be sent
    timeOfDayOfReminder: Calendar object for when the reminder should be sent
     */
    HashMap<String, Boolean> notifications;
    Boolean lightTheme;
    int daysBeforeReminder;
    Calendar timeOfDayOfReminder;

    // Constructor to initialize the object
    public Preferences() {
        notifications = new HashMap<String, Boolean>();

        lightTheme = true;

        // Default reminder 3 days before expiration date
        daysBeforeReminder = 3;

        // Default reminder at 9:00 AM
        timeOfDayOfReminder = Calendar.getInstance();
        timeOfDayOfReminder.set(Calendar.HOUR_OF_DAY, 9);
        timeOfDayOfReminder.set(Calendar.MINUTE, 0);
        timeOfDayOfReminder.set(Calendar.SECOND, 0);
    }

    // Getters and setters

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

    public Calendar getTimeOfDayOfReminder() {
        return timeOfDayOfReminder;
    }

    public void setTimeOfDayOfReminder(Calendar timeOfDayOfReminder) {
        this.timeOfDayOfReminder = timeOfDayOfReminder;
    }
}
