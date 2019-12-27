package com.example.givrish.interfaces;

import com.example.givrish.models.AllItemsResponseData;

import java.util.List;

public interface ListCallBackEvent {
  void itemsLoaded(List<AllItemsResponseData> items);
}
