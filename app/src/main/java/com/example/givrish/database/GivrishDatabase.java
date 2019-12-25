package com.example.givrish.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ItemCategoryData.class, ItemSubCategoryData.class}, version = 1)
public abstract class GivrishDatabase extends RoomDatabase {
  public abstract CategoriesDao categoriesDao();
  private static final String DB_NAME = "Gi";

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
