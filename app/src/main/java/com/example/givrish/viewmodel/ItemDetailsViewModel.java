package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.givrish.database.CategoriesRepository;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;

public class ItemDetailsViewModel extends AndroidViewModel {
  private CategoriesRepository categoriesRepository;
  private ItemCategoryData categoryName;

  public ItemDetailsViewModel(@NonNull Application application) {
    super(application);
    categoriesRepository = new CategoriesRepository(application);
  }

  public ItemCategoryData getCategory(String id) {
    return categoriesRepository.getCategory(id);
  }

  public ItemSubCategoryData getSubCategory(String id) {
    return categoriesRepository.getSubCategory(id);
  }
}
