package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.givrish.database.CategoriesRepository;
import com.example.givrish.models.ItemSubCategoryData;

import java.util.List;

public class ItemSubCategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private CategoriesRepository categoriesRepository;
    private LiveData<List<ItemSubCategoryData>> itemSubCategories;

    public ItemSubCategoryViewModel(Application applicationn){
        super(applicationn);
        categoriesRepository = new CategoriesRepository(applicationn);
        itemSubCategories = categoriesRepository.getAllSub();
    }
    public void insertAllSub (List<ItemSubCategoryData> itemSubCategoryData) {
        categoriesRepository.insertSub(itemSubCategoryData);
    }


    public LiveData<List<ItemSubCategoryData>> getLiveSubCategories() {return itemSubCategories;}


}
