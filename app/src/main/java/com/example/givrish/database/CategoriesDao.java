package com.example.givrish.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;


import java.util.List;

@Dao
public interface CategoriesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAllCategories(List<ItemCategoryData> itemCategoryData);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAllSub(List<ItemSubCategoryData> itemCategoryData);


  @Query("SELECT * FROM item_sub_category")
  LiveData<List<ItemSubCategoryData>> getAllSub();

  @Query("SELECT * FROM item_category")
  LiveData<List<ItemCategoryData>> getAllCategories();

  @Query("SELECT COUNT(*) FROM item_category")
  LiveData<Integer> getCount();
  

}
