package com.example.givrish.network;

import com.example.givrish.models.UserRegisterResponseDto;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpointInterface {
    @POST("userlogin/add_user")
    Call<UserRegisterResponseDto> createUser(@Query("jsonString") String jsonString);


    @GET("userlogin/get_user_login")
    Call<UserRegisterResponseDto> checkUser(@Query("jsonString") String jsonString);

}
