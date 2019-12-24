package com.example.givrish.network;

import com.example.givrish.models.AddItemResponse;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.models.LoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpointInterface {
  @POST("userlogin/add_user")
  Call<AuthResponseDto> createUser(@Query("jsonString") String jsonString);

  @GET("userlogin/get_user_login")
  Call<AuthResponseDto> checkUser(@Query("jsonString") String jsonString);

  @POST("userlogin/login_user")
  Call<LoginResponse> login(@Query("jsonString") String jsonString);

  @POST("itemscontroller/get_all_item")
  Call<AllItemsResponse> getAllItems(@Query("jsonString") String jsonString);

  @GET("itemcategory/get_category")
  Call<ItemCategoryResponse> getCategory(@Query("jsonString") String jsonString);

  @GET("itemcategory/get_sub_category")
  Call<ItemSubCategoryResponse> getSubCategory(@Query("jsonString") String jsonString);

  @POST("/itemscontroller/load_item_details")
  Call<List<AddItemResponse>> addItem(@Query("jsonString") String jsonString);

  @GET("{imageName}")
  Call<ResponseBody> getImage(@Path("imageName") String image);

  @Multipart
  @POST("itemscontroller/load_item_images")
  Call<List<AddItemResponse>> uploadImage(@Part MultipartBody.Part file, @Part("name")RequestBody requestBody, @Query("item_id") String id);

}
