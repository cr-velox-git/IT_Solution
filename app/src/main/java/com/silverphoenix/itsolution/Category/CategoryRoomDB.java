package com.silverphoenix.itsolution.Category;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Add data base Entity
@Database(entities  = {CategoryData.class}, version = 1, exportSchema = false)
public abstract class CategoryRoomDB extends RoomDatabase {
    //Create database instance
    private  static CategoryRoomDB database;
    //Define database name
    private static  String DATABASE_NAME = "category_database";

    public synchronized  static CategoryRoomDB getInstance(Context context){
        //Check condition
        if (database == null){
            //when database is null
            // Intitaialize database
            database = Room.databaseBuilder(context.getApplicationContext(),
                    CategoryRoomDB.class,DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        // Return database
        return database;
    }

    public abstract CategoryDao categoryDao();
}
