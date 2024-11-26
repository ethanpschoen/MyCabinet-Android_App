package com.example.mycabinet;
import java.time.LocalDate;

public class FoodItem {

    private String itemName;
    private LocalDate expirationDate;
    private String itemNotes;

    // Constructor to initialize the object
    public FoodItem(String itemName, LocalDate expirationDate) {
        this.itemName = itemName;
        this.expirationDate = expirationDate;
    }

    // Constructor with additional notes
    public FoodItem(String itemName, LocalDate expirationDate, String itemNotes) {
        this.itemName = itemName;
        this.expirationDate = expirationDate;
        this.itemNotes = itemNotes;
    }

    // Getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getItemNotes() {
        return itemNotes;
    }

    public void setItemNotes(String itemNotes) {
        this.itemNotes = itemNotes;
    }
}