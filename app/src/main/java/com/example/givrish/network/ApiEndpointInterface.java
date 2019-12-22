package com.example.givrish.network;

import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.models.LoginResponse;

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
    Call<LoginResponse> login(@Query("jsonString") String jsonString);

    @GET("itemcategory/get_category")
    Call<ItemCategoryResponse> getCategory(@Query("jsonString")String jsonString);
	
    @GET("itemcategory/get_sub_category")
	Call<ItemSubCategoryResponse> getSubCategory(@Query("jsonString")String jsonString);

}
