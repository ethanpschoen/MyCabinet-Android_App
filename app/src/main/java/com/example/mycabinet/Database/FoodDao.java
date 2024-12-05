package com.example.mycabinet.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FoodDao {
    @Insert
    void insertAll(ReminderClass reminderClass);

    @Query("SELECT * FROM reminders")
    List<ReminderClass> getAllData();
}
