package com.example.mycabinet;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

public class FoodItem {

    String itemName;
    LocalDate expirationDate;
    String itemNotes;

    public FoodItem(String itemName, LocalDate expirationDate) {
        this.itemName = itemName;
        this.expirationDate = expirationDate;
    }

}
