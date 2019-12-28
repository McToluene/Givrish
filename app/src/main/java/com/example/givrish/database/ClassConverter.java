package com.example.givrish.database;

import androidx.room.TypeConverter;

import com.example.givrish.models.AllItemsResponseImageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class ClassConverter {
  @TypeConverter
  public static String toString(List<AllItemsResponseImageData> imageData){
    try {
      return new Gson().toJson(imageData);
    }catch (Exception e){
      return null;
    }
  }

  @TypeConverter
  public static List<AllItemsResponseImageData> fromList(String str) {
    try {
        return new Gson().fromJson(str, new TypeToken<List<AllItemsResponseImageData>>(){}.getType());
    }catch (Exception e){
      return null;
    }
  }
}



