package com.example.givrish.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.givrish.models.AllItemsResponseData;

import java.util.List;

@Dao
public interface ItemsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(List<AllItemsResponseData> itemsResponseData);

  @Query("SELECT * FROM item")
  LiveData<List<AllItemsResponseData>> getAllItems();
}
