package com.silverphoenix.itsolution.Product;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface ProductDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(ProductData productData);

    //Delete query
    @Delete
    void delete(ProductData productData);

    //Delete all query
    @Delete
    void reset(List<ProductData> productData);

    //Update query
    @Query("UPDATE product_data SET name = :sName , image = :sImage, image1 = :sImage1, image2 = :sImage2, mrp = :sMrp, discount = :sDiscount, sellprice = :sSellprice WHERE ID = :sID")
    void update(int sID, String sName, String sImage, String sImage1, String sImage2, String sMrp, String sDiscount, String sSellprice);

    //get all data query
    @Query("SELECT * FROM product_data")
    List<ProductData> getAll();
}
