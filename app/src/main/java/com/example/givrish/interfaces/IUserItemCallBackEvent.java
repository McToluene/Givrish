package com.example.givrish.interfaces;

import com.example.givrish.models.GetUserItemResponseData;

import java.util.List;

public interface IUserItemCallBackEvent {
    void userItemsLoaded(List<GetUserItemResponseData> items);
}
