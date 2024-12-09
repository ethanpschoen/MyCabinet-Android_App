package com.example.mycabinet;

import java.util.Calendar;
import java.util.HashMap;


public class Preferences {

    /*
    Member variables

    daysBeforeReminder: The number of days before an item's expiration date to send a reminder
    timeOfDayOfReminder: The time of day to send a reminder (in 24-hour format; military time)
    lightTheme: Whether or not to use the light theme, this is the default and there is no alternative was not utilized
    notifications: a hashmap that stores app notifications
     */
    HashMap<String, Boolean> notifications;
    Boolean lightTheme;
    int daysBeforeReminder;
    Calendar timeOfDayOfReminder;

    public Preferences() {
        notifications = new HashMap<String, Boolean>();
        lightTheme = true;
        daysBeforeReminder = 3;
        //this is getting the minute, hour and seconds of the day for the reminder to be sent
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
    //method to get the time of day of the reminder
    public Calendar getTimeOfDayOfReminder() {
        return timeOfDayOfReminder;
    }

    public void setTimeOfDayOfReminder(Calendar timeOfDayOfReminder) {
        this.timeOfDayOfReminder = timeOfDayOfReminder;
    }
}
