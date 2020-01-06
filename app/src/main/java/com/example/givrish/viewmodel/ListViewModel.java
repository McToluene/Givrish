package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.givrish.database.ItemsRepository;
import com.example.givrish.models.AllItemsResponseData;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
  private ItemsRepository itemsRepository;
  private LiveData<List<AllItemsResponseData>> items;

  public ListViewModel(@NonNull Application application) {
    super(application);
    itemsRepository = new ItemsRepository(application);
    items = itemsRepository.getItems();
  }

  // TODO: Implement the ViewModel
  public void insertAllItems(List<AllItemsResponseData> items) {
    itemsRepository.setItems(items);
  }

  public LiveData<List<AllItemsResponseData>> getItems() {return items;}
}
