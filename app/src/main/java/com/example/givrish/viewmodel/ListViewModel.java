package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.givrish.database.ItemRepository;
import com.example.givrish.models.AllItemsResponseData;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
  private ItemRepository itemRepository;
  private LiveData<List<AllItemsResponseData>> items;

  public ListViewModel(@NonNull Application application) {
    super(application);
    itemRepository = new ItemRepository(application);
    items = itemRepository.getItems();
  }

  // TODO: Implement the ViewModel
  public void insertAllItems(List<AllItemsResponseData> items) {itemRepository.setItems(items);}

  public LiveData<List<AllItemsResponseData>> getItems() {return items;}
}
