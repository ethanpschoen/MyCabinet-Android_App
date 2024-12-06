package com.example.mycabinet;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Kitchen {

    private static Kitchen instance;

    private ArrayList<FoodSection> sections;
    private Preferences settings;

    private Kitchen() {
        sections = new ArrayList<FoodSection>();
        settings = new Preferences();
    }

    public static Kitchen getInstance() {
        if (instance == null) {
            instance = new Kitchen();
        }
        return instance;
    }

    public void addSection(FoodSection section) {
        sections.add(section);
    }

    public FoodSection getSection(String name) {
        for (FoodSection section : sections) {
            if (section.getSectionName().equals(name)) {
                return section;
            }
        }
        return null;
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
}
