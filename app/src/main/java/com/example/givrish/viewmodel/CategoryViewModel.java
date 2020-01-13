package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.givrish.database.CategoriesRepository;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

  private CategoriesRepository categoriesRepository;
  private LiveData<List<ItemCategoryData>> itemCategories;
  private LiveData<List<ItemSubCategoryData>> itemSubCategories;

  public CategoryViewModel(Application application) {
    super(application);
    categoriesRepository = new CategoriesRepository(application);
    itemCategories = categoriesRepository.getAllCategories();
    itemSubCategories = categoriesRepository.getAllSub();
  }

  public LiveData<List<ItemCategoryData>> getAllCategories() {return itemCategories;}

  public void insert(List<ItemCategoryData> itemCategoryData) {categoriesRepository.insert(itemCategoryData);}

  public LiveData<Integer> getCount() { return categoriesRepository.getCount();}

  public LiveData<List<ItemSubCategoryData>> getCategorySub(String id) {
    return categoriesRepository.getCategorySub(id);
  }

  public void insertAllSub (List<ItemSubCategoryData> itemSubCategoryData) {
    categoriesRepository.insertSub(itemSubCategoryData);
  }

  public LiveData<List<ItemSubCategoryData>> getAllSub() {
    return itemSubCategories;
  }
}
