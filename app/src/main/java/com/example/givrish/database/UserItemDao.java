package com.example.givrish.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.givrish.models.GetUserItemResponseData;

import java.util.List;

@Dao
public interface UserItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserItem(List<GetUserItemResponseData> userItemResponseData);

    @Query("SELECT * FROM user_item")
    LiveData<List<GetUserItemResponseData>> getUserItem();
}
