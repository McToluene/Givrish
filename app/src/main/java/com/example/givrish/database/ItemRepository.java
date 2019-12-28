package com.example.givrish.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.givrish.models.AllItemsResponseData;

import java.util.List;

public class ItemRepository {
  private GivrishDatabase database;
  private ItemsDao itemsDao;
  private LiveData<List<AllItemsResponseData>> items;

  public ItemRepository(Application application) {
    database = GivrishDatabase.getDatabase(application);
    itemsDao = database.itemsDao();
    items = itemsDao.getAllItems();
  }

  public LiveData<List<AllItemsResponseData>> getItems() {return items;}

  public void setItems(final List<AllItemsResponseData> items) {
    GivrishDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        database.itemsDao().insertAll(items);
      }
    });
  }
}
