package com.example.givrish.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.givrish.models.ItemCategoryData;

import java.util.List;

@Dao
public interface CategoriesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(List<ItemCategoryData> itemCategoryData);

  @Query("SELECT * FROM item_category")
  LiveData<List<ItemCategoryData>> getAll();

  @Query("SELECT COUNT(*) FROM item_category")
  LiveData<Integer> getCount();

}
