package com.example.givrish.network;

import com.example.givrish.models.AddItemResponse;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.models.JsonResponse;
import com.example.givrish.models.LoginResponse;
import com.example.givrish.models.ProfileEditResponse;
import com.example.givrish.models.SignUpResponse;
import com.example.givrish.models.UserData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiEndpointInterface {
  @POST("userlogin/add_user")
  Call<JsonResponse<SignUpResponse>> createUser(@Query("jsonString") String jsonString);

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

  @Multipart
  @POST("itemscontroller/load_item_images")
  Call<List<AddItemResponse>> uploadImage(@Part MultipartBody.Part file, @Part("name")RequestBody requestBody, @Query("item_id") String id);

  @Multipart
  @POST("userlogin/user_profile_picture")
  Call<List<ProfileEditResponse>> updateProfilePix(@Part MultipartBody.Part file, @Part("name")RequestBody requestBody, @Query("user_id") String id);

  @GET("profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/")
  Call<ProfileEditResponse> getImageDetails(@Query("jsonString") String jsonString);

 /* @GET("api/RetrofitAndroidImageResponse")
  Call<ResponseBody> getImageDetails();*/

}
