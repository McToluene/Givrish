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

  @Query("SELECT * FROM item_sub_category order by item_sub_category_name")
  LiveData<List<ItemSubCategoryData>> getAllSub();

  @Query("SELECT * FROM item_category order by item_category_name")
  LiveData<List<ItemCategoryData>> getAllCategories();

  @Query("SELECT COUNT(*) FROM item_category")
  LiveData<Integer> getCount();

  @Query("SELECT COUNT(*) FROM item_sub_category")
  LiveData<Integer> getSubCount();

  @Query("SELECT * FROM item_sub_category WHERE item_category_id = :item_category_id order by item_sub_category_name")
  LiveData<List<ItemSubCategoryData>> getSub(String item_category_id);

  @Query("SELECT * FROM item_category WHERE item_category_id = :id")
  ItemCategoryData getCategory(String id);

  @Query("SELECT * FROM item_sub_category WHERE item_sub_category_id = :id")
  ItemSubCategoryData getSubCategory(String id);

}
