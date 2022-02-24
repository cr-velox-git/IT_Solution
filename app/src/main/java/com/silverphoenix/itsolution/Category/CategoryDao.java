package com.silverphoenix.itsolution.Category;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(CategoryData categoryData);

    //Delete query
    @Delete
    void delete(CategoryData categoryData);

    //Delete all query
    @Delete
    void reset(List<CategoryData> categoryData);

    //Update query
    @Query("UPDATE category_data SET name = :sName , image = :sImage WHERE ID = :sID")
    void update(int sID, String sName, String sImage);

    //get all data query
    @Query("SELECT * FROM category_data")
    List<CategoryData> getAll();
}
