package com.example.mycabinet;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Kitchen implements Parcelable {

    private ArrayList<FoodSection> sections;
    private Preferences settings;

    public Kitchen() {
        sections = new ArrayList<FoodSection>();
        settings = new Preferences();
    }

    public void addSection(FoodSection section) {
        sections.add(section);
    }

    public void removeSection(FoodSection section) {
        sections.remove(section);
    }

    public void clearSections() {
        sections.clear();
    }

    public ArrayList<FoodSection> getSections() {
        return sections;
    }

    public void setSettings(Preferences settings) {
        this.settings = settings;
    }

    public Preferences getSettings() {
        return settings;
    }

    protected Kitchen(Parcel in) {
        sections = in.createTypedArrayList(FoodSection.CREATOR);
        settings = in.readParcelable(Preferences.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(sections);
        dest.writeParcelable(settings, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Kitchen> CREATOR = new Creator<Kitchen>() {
        @Override
        public Kitchen createFromParcel(Parcel in) {
            return new Kitchen(in);
        }

        @Override
        public Kitchen[] newArray(int size) {
            return new Kitchen[size];
        }
    };
}
