package com.example.mycabinet.Database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class ReminderClass {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "foodname")
    String foodname;
    @ColumnInfo(name = "fooddate")
    String fooddate;
    @ColumnInfo(name = "foodtime")
    String foodtime;


    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }
    public String getFoodName() {
        return foodname;
    }

    public void setFoodName(String foodname){
        this.foodname = foodname;
    }

    public String getFoodDate() {
        return fooddate;
    }

    public void setFoodDate(String fooddate){
        this.fooddate = fooddate;
    }

    public String getFoodTime() {
        return foodtime;
    }

    public void setFoodTime(String foodtime){
        this.foodtime = foodtime;
    }
}
