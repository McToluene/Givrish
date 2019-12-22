package com.example.givrish.database;

import com.example.givrish.ItemSubCategoryData;
import com.example.givrish.models.ItemCategoryData;

import java.util.List;

public interface CategoryCallbackEvent {
    void BackgroundResult(String id, ItemSubCategoryData msg);
    void BackgroundResult(String id, ItemCategoryData msg);
    void BackgroundResult(String id, List<ItemCategoryData> msg);

    void BackgroundResult(String s, String item_category_id);
}
