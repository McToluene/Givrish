package com.example.givrish.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;

import java.util.List;

public class CategoriesRepository {
  private CategoriesDao categoriesDao;
  private LiveData<List<ItemCategoryData>> listLiveDataCategories;
  private LiveData<List<ItemSubCategoryData>> listLiveDataSub;

  private LiveData<Integer> count;
  private LiveData<Integer> subCount;

  public CategoriesRepository(Application application) {
    GivrishDatabase db = GivrishDatabase.getDatabase(application);
    categoriesDao = db.categoriesDao();

    listLiveDataCategories = categoriesDao.getAllCategories();
    listLiveDataSub = categoriesDao.getAllSub();

    count = categoriesDao.getCount();
    subCount = categoriesDao.getSubCount();

  }

  public LiveData<Integer> getCount() {return count;}
  public LiveData<Integer> getSubCount(){return subCount;}


  public LiveData<List<ItemCategoryData>> getAllCategories() {
    return listLiveDataCategories;
  }
  public LiveData<List<ItemSubCategoryData>> getAllSub() {return listLiveDataSub;}

  public ItemCategoryData getCategory(String id) {
    return categoriesDao.getCategory(id);
  }

  public ItemSubCategoryData getSubCategory(String id) {return categoriesDao.getSubCategory(id); }

  public LiveData<List<ItemSubCategoryData>> getCategorySub(String id) {return categoriesDao.getCategorySub(id);}

  public void insert(final List<ItemCategoryData> itemCategoryData) {
    GivrishDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        categoriesDao.insertAllCategories(itemCategoryData);
      }
    });
  }

  public void insertSub(final List<ItemSubCategoryData> itemSubCategoryData) {
    GivrishDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        categoriesDao.insertAllSub(itemSubCategoryData);
      }
    });
  }


}
