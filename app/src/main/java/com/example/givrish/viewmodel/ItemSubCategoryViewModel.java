package com.example.givrish.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.givrish.database.CategoriesRepository;
import com.example.givrish.models.ItemSubCategoryData;
import com.example.givrish.ui.ItemSubCategoryFragment;

import java.util.List;

public class ItemSubCategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private CategoriesRepository categoriesRepository;
    private LiveData<List<ItemSubCategoryData>> subsub;

    public ItemSubCategoryViewModel(Application applicationn){
        super(applicationn);
        categoriesRepository = new CategoriesRepository(applicationn);
        subsub = categoriesRepository.getSub(ItemSubCategoryFragment.ademi);
    }

    public LiveData<List<ItemSubCategoryData>> getSubsub(){return subsub;}


    public void insertAllSub (List<ItemSubCategoryData> itemSubCategoryData) {
        categoriesRepository.insertSub(itemSubCategoryData);
    }

    public LiveData<Integer> getSubCount() {return categoriesRepository.getSubCount();}

}
