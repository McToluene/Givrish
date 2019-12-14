package com.example.givrish.network;

import com.example.givrish.models.AuthResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpointInterface {
    @POST("userlogin/add_user")
    Call<AuthResponseDto> createUser(@Query("jsonString") String jsonString);


    @GET("userlogin/get_user_login")
    Call<AuthResponseDto> checkUser(@Query("jsonString") String jsonString);

    @POST("userlogin/login_user")
    Call<AuthResponseDto> login(@Query("jsonString") String jsonString);

}
