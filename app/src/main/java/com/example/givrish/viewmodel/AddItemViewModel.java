package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.givrish.database.CategoriesRepository;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;

import java.util.List;

public class AddItemViewModel extends AndroidViewModel {
  // TODO: Implement the ViewModel

  private LiveData<List<ItemCategoryData>> liveItemCategories;
  private LiveData<List<ItemSubCategoryData>> liveSubCategories;
  private CategoriesRepository categoriesRepository;


  public AddItemViewModel(Application application) {
    super(application);
    categoriesRepository = new CategoriesRepository(application);
    liveItemCategories = categoriesRepository.getAllCategories();
    liveSubCategories = categoriesRepository.getAllSub();
  }

  public LiveData<List<ItemCategoryData>> getLiveItemCategories() {return liveItemCategories;}

  public LiveData<List<ItemSubCategoryData>> getLiveSubCategories() {return liveSubCategories;}

  public void insertAll(List<ItemCategoryData> itemCategoryData) {
    categoriesRepository.insert(itemCategoryData);
  }

  public void insertAllSub (List<ItemSubCategoryData> itemSubCategoryData) {
    categoriesRepository.insertSub(itemSubCategoryData);
  }
  public ItemSubCategoryData getSubCategory(String id) {
    return categoriesRepository.getSubCategory(id);
  }
}
