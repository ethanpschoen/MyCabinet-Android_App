package com.example.mycabinet;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class FoodItem implements Parcelable {

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

    protected FoodItem(Parcel in) {
        itemName = in.readString();
        expirationDate = LocalDate.parse(in.readString());
        itemNotes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(expirationDate.toString());
        dest.writeString(itemNotes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };
}