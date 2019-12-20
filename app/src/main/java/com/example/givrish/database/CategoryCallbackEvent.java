package com.example.givrish.database;

import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryModel;

public interface CategoryCallbackEvent {
    void BackgroundResult(String id, ItemSubCategoryModel msg);
    void BackgroundResult(String id, ItemCategoryData msg);
}
