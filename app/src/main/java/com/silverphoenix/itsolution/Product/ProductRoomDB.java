package com.silverphoenix.itsolution.Product;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.silverphoenix.itsolution.Category.CategoryDao;
import com.silverphoenix.itsolution.Category.CategoryData;

// Add data base Entity
@Database(entities  = {ProductData.class}, version = 1, exportSchema = false)
public abstract class ProductRoomDB extends RoomDatabase {
    //Create database instance
    private  static ProductRoomDB database;
    //Define database name
    private static  String DATABASE_NAME = "product_database";

    public synchronized  static ProductRoomDB getInstance(Context context){
        //Check condition
        if (database == null){
            //when database is null
            // Intitaialize database
            database = Room.databaseBuilder(context.getApplicationContext(),
                    ProductRoomDB.class,DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        // Return database
        return database;
    }

    public abstract ProductDao productDao();
}
