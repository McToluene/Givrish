package com.example.givrish.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.givrish.models.ItemCategoryData;

import java.util.List;

public class CategoriesRepository {
  private CategoriesDao categoriesDao;
  private LiveData<List<ItemCategoryData>> listLiveDataCategories;
  private LiveData<Integer> count;

  public CategoriesRepository(Application application) {
    GivrishDatabase db = GivrishDatabase.getDatabase(application);
    categoriesDao = db.categoriesDao();
    listLiveDataCategories = categoriesDao.getAll();
    count = categoriesDao.getCount();
  }

  public LiveData<Integer> getCount() {return count;}
  public LiveData<List<ItemCategoryData>> getAll() {
    return listLiveDataCategories;
  }
  public void insert(final List<ItemCategoryData> itemCategoryData) {
    GivrishDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        categoriesDao.insertAll(itemCategoryData);
        Log.i("Here", "Thread");
      }
    });
  }
}
