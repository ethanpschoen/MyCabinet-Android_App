package com.example.mycabinet;

import java.util.ArrayList;

public class Kitchen {

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
}
