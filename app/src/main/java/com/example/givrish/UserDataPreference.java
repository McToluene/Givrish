package com.example.givrish;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.givrish.models.UserLoginModel;
import com.example.givrish.models.UserRegisterModel;

public class UserDataPreference {
    private static final String DEFAULT_VALUE = " ";
    private static UserDataPreference outInstance = new UserDataPreference();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    private UserDataPreference(){}

    public static UserDataPreference getInstance(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit(); }
        return outInstance;
    }

  public void savePreference(String key,String value){
        editor.putString(key,value);
        editor.commit();
  }

    public  void deletePreference(String Key) {
        editor.remove(Key);
        editor.commit();
    }

  public void clearPreference(String key){
        editor.clear();
        editor.commit();
  }


  public String retrievePreference(String key){
        return sharedPreferences.getString(key,DEFAULT_VALUE);
  }

}
