package com.example.givrish.interfaces;

import com.example.givrish.models.AllItemsResponseData;

import java.util.List;

public interface SimilarItemsCallBack {
  void filterItems( List<AllItemsResponseData> items);

  void removeTop(List<AllItemsResponseData> similarItems);

  void loadItems(List<AllItemsResponseData> subList);
}
