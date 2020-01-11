package com.example.givrish.database;

import androidx.room.TypeConverter;
import com.example.givrish.models.GetUserItemImageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class UserItemConverter {

    @TypeConverter
    public static String toString(List<GetUserItemImageData> imageData){
        try {
            return new Gson().toJson(imageData);
        }catch (Exception e){
            return null;
        }
    }

    @TypeConverter
    public static List<GetUserItemImageData> fromList(String str) {
        try {
            return new Gson().fromJson(str, new TypeToken<List<GetUserItemImageData>>(){}.getType());
        }catch (Exception e){
            return null;
        }
    }
}
