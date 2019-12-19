package com.example.givrish;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.givrish.models.UserData;
import com.example.givrish.models.UserLoginModel;
import com.example.givrish.models.UserRegisterModel;

public class UserDataPreference {
    private static final String SHARED_PREF_NAME ="Ayodeji";
    private static UserDataPreference mInstance;
    private Context mCtx;
        private static final String DEFAULT_VALUE = " ";



    private UserDataPreference(Context mCtx){
        this.mCtx = mCtx;
    }

    public static synchronized UserDataPreference getInstance(Context mCtx){

        if(mInstance == null){mInstance = new UserDataPreference(mCtx);}
        return mInstance;
    }

    public void saveUser(UserData userData){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone_number",userData.getPhone_number());
        editor.putString("password",userData.getPassword());
        editor.apply();
    }

    public String isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id","1");
 }

    public UserLoginModel getLoggedInUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new UserLoginModel(
                sharedPreferences.getString("phone_number",DEFAULT_VALUE),
                sharedPreferences.getString("password",DEFAULT_VALUE)
        );
    }




    public UserRegisterModel getRegisterdUser(){
     SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
     return new UserRegisterModel(
                sharedPreferences.getString("fullname",DEFAULT_VALUE),
                sharedPreferences.getString("password",DEFAULT_VALUE),
                sharedPreferences.getString("phone_number",DEFAULT_VALUE)

        );
     }

     public  void clear(){
         SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();


     }



//    private static final String DEFAULT_VALUE = " ";
//    private static UserDataPreference outInstance = new UserDataPreference();
//    private static SharedPreferences sharedPreferences;
//    private static SharedPreferences.Editor editor;
//
//
//    private UserDataPreference(){}
//
//    public static UserDataPreference getInstance(Context context){
//        if(sharedPreferences == null){
//            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
//            editor = sharedPreferences.edit();
//        }
//
//        return outInstance;
//    }
//
//  public void savePreference(String key,String value){
//        editor.putString(key,value);
//        editor.commit();
//  }
//  public void saveUser(UserData userData){
//        editor.putString("fullname",userData.getFullname());
//        editor.putString("password",userData.getPassword());
//        editor.putString("phone_number",userData.getPhone_number());
//      editor.putString("user_id",userData.getUser_id());
//      editor.apply();
//  }


// public String isLoggedIn(){
//        return sharedPreferences.getString("user_id","1");
// }
//
// public UserData getUser(){
//        return new UserData(
//                sharedPreferences.getString("fullname",DEFAULT_VALUE),
//                sharedPreferences.getString("password",DEFAULT_VALUE),
//                sharedPreferences.getString("phone_number",DEFAULT_VALUE),
//                sharedPreferences.getString("user_id",DEFAULT_VALUE)
//        );
//     }
//
//
//
//    public UserLoginModel getUserLogin(){
//        return new UserLoginModel(
//                sharedPreferences.getString("password",DEFAULT_VALUE),
//                sharedPreferences.getString("phone_number",DEFAULT_VALUE)
//        );
//  }
//
//    public  void deletePreference(String Key) {
//        editor.remove(Key);
//        editor.commit();
//    }
//
//  public void clearPreference(String key){
//        editor.clear();
//        editor.commit();
//  }
//
//
//  public String retrievePreference(String key){
//        return sharedPreferences.getString(key,DEFAULT_VALUE);
//  }



}
