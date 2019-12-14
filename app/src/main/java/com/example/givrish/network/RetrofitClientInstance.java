package com.example.givrish.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientInstance {
  private static Retrofit retrofit;
  private static  final  String BASE_URL = "http://10.10.10.3:8080/givrish_api/";

  public static Retrofit getRetrofitInstance () {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .addConverterFactory(ScalarsConverterFactory.create())
              .build();
    }
    return retrofit;
  }
}
