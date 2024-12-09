package com.example.mycabinet;

import java.time.LocalDate;
import java.util.UUID;

/*
The FoodItem model class represents an food product in the Kitchen.
It has a name, expiration date, and any additional notes.
It is used in the FoodSection model class.
 */

public class FoodItem {

    /*
    Member variables

    itemName: String representing the name of the food item
    expirationDate: LocalDate representing the expiration date of the food item
    itemNotes: String representing any additional (optional) notes about the food item
     */
    private String itemName;
    private LocalDate expirationDate;
    private String itemNotes;
    private String itemID;


    // Constructor to initialize the object
    public FoodItem(String itemName, LocalDate expirationDate) {
        this.itemName = itemName;
        this.expirationDate = expirationDate;
        this.itemNotes = "";
        this.itemID = UUID.randomUUID().toString();
    }

    // Constructor with additional notes
    public FoodItem(String itemName, LocalDate expirationDate, String itemNotes) {
        this.itemName = itemName;
        this.expirationDate = expirationDate;
        this.itemNotes = itemNotes;
        this.itemID = UUID.randomUUID().toString();
    }


    // Getters and setters to set and get the member variables for food item

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

    public String getItemID() {
        return itemID;
    }
}