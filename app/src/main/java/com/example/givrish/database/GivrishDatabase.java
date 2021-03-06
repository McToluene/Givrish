package com.example.givrish.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.AllItemsResponseImageData;
import com.example.givrish.models.GetUserItemImageData;
import com.example.givrish.models.GetUserItemResponseData;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ItemCategoryData.class, ItemSubCategoryData.class, AllItemsResponseData.class, AllItemsResponseImageData.class, GetUserItemResponseData.class, GetUserItemImageData.class}, version = 1)
@TypeConverters({ClassConverter.class, UserItemConverter.class})
public abstract class GivrishDatabase extends RoomDatabase {
  public abstract CategoriesDao categoriesDao();
  public abstract ItemsDao itemsDao();
  public abstract UserItemDao userItemDao();

  private static final String DB_NAME = "Givrish";
  private static volatile GivrishDatabase INSTANCE;
  private static  final int NUMBER_OF_THREADS = 4;

  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
  static GivrishDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (GivrishDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                  GivrishDatabase.class, DB_NAME)
                  .build();
        }
      }
    }
    return INSTANCE;
  }
}