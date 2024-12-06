package com.example.mycabinet;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

import java.util.HashMap;

// Change this set-up to just use SharedPreferences?
public class Preferences implements Parcelable {
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

    protected Preferences(Parcel in) {
        notifications = (HashMap<String, Boolean>) in.readSerializable();
        lightTheme = in.readByte() != 0;
        daysBeforeReminder = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(notifications);
        dest.writeByte((byte) (lightTheme ? 1 : 0));
        dest.writeInt(daysBeforeReminder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Preferences> CREATOR = new Creator<Preferences>() {
        @Override
        public final Preferences createFromParcel(Parcel in) {
            return new Preferences(in);
        }

        @Override
        public final Preferences[] newArray(int size) {
            return new Preferences[size];
        }
    };
}
