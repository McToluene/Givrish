package com.example.givrish.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.GetUserItemResponseData;

import java.util.List;

public class UserItemRepository {

    private UserItemDao itemsDao;
    private LiveData<List<GetUserItemResponseData>> items;

    public UserItemRepository(Application application) {
        GivrishDatabase database = GivrishDatabase.getDatabase(application);
        itemsDao = database.userItemDao();
        items = itemsDao.getUserItem();
    }

    public void setItems(final List<GetUserItemResponseData> items) {
        GivrishDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemsDao.insertUserItem(items);
            }
        });
    }

    public LiveData<List<GetUserItemResponseData>> getItems() {
        return items;
    }
}
