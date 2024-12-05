package com.example.mycabinet.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ReminderClass.class}, version = 1)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract FoodDao foodDao();
    private static DatabaseClass INSTANCE;

    static DatabaseClass getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseClass.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseClass.class, "food_database")
                            .build();
                }
            }

        }
        return INSTANCE;
    }
}
