package com.example.givrish.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
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
}
