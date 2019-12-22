package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.givrish.database.CategoriesRepository;
import com.example.givrish.models.ItemCategoryData;

import java.util.Collections;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

  private CategoriesRepository categoriesRepository;
  private LiveData<List<ItemCategoryData>> itemCategories;

  public CategoryViewModel(Application application) {
    super(application);

    categoriesRepository = new CategoriesRepository(application);
    itemCategories = categoriesRepository.getAll();


  }

  public LiveData<List<ItemCategoryData>> getAllCategories() {return itemCategories;}
  public void insert(List<ItemCategoryData> itemCategoryData) {categoriesRepository.insert(itemCategoryData);}
  public LiveData<Integer> getCount() { return categoriesRepository.getCount();}

}
