package com.example.givrish.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.givrish.database.ItemsRepository;
import com.example.givrish.database.UserItemRepository;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.GetUserItemResponseData;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private UserItemRepository itemsRepository;
    private LiveData<List<GetUserItemResponseData>> items;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        itemsRepository = new UserItemRepository(application);
        items = itemsRepository.getItems();
    }

  // TODO: Implement the ViewModel
    private final String KEY_VERIFY_CODE = "com.example.givrish.viewmodel.VERIFYKEY";
    private final String KEY_PhoneNumber = "com.example.givrish.viewmodel.Vphonenumber";
    private final String KEY_UserFullname = "com.example.givrish.viewmodel.userFullname";
    private final String KEY_UserPasswrd = "com.example.givrish.viewmodel.userPassword";


    public String originalKey;
    public String originalPhoneNumber;
    public String originalUserName;
    public String originalUserPassword;


    public void saveOriginalState(Bundle outState) {
              outState.putString(KEY_VERIFY_CODE,originalKey);
              outState.putString(KEY_PhoneNumber,originalPhoneNumber);
              outState.putString(KEY_UserFullname,originalUserName);
              outState.putString(KEY_UserPasswrd,originalUserPassword);

    }

    public void RestoreOriginalState(Bundle savedInstanceState) {
               originalKey = savedInstanceState.getString(KEY_VERIFY_CODE);
               originalPhoneNumber  = savedInstanceState.getString(KEY_PhoneNumber);
               originalUserName = savedInstanceState.getString(KEY_UserFullname);
               originalUserPassword = savedInstanceState.getString(KEY_UserPasswrd);

    }

    public LiveData<List<GetUserItemResponseData>> getItems() {
        return items;
    }

    public void insertAllItems(List<GetUserItemResponseData> newItems) {
        itemsRepository.setItems(newItems);
    }
}
