package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.JsonResponse;
import com.example.givrish.models.SignUpResponse;
import com.google.android.material.snackbar.Snackbar;

import com.example.givrish.models.UserRegisterModel;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
  private ApiEndpointInterface apiService;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);

    final TextInputEditText fullName = findViewById(R.id.ed_fullName);
    final TextInputEditText number = findViewById(R.id.edt_phoneNumber);
    final TextInputEditText password = findViewById(R.id.ed_password);
    final TextInputEditText comPassword = findViewById(R.id.ed_compassword);
    MaterialButton signUp = findViewById(R.id.btn_signUp);

    String phone = getIntent().getStringExtra(PhoneVerifyActivity.phoneverifyKey);
    if(phone != null) number.setText(phone);

    signUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String name= "";
        String phone ="";
        String pass ="";
        String comPass = "";

        if (fullName.getText() != null)
          name = fullName.getText().toString();

        if (number.getText() != null)
          phone = number.getText().toString();

        if (password.getText() != null)
          pass = password.getText().toString();

        if (comPassword.getText() != null)
          comPass = comPassword.getText().toString();



        if (name.isEmpty() || pass.isEmpty() || comPass.isEmpty() || phone.isEmpty()){
          Snackbar.make(v,getResources().getString(R.string.empty_error), Snackbar.LENGTH_LONG).show();
        } else if (!pass.equals(comPass)){
          Snackbar.make(v,getResources().getString(R.string.password_error), Snackbar.LENGTH_LONG).show();
        }else {
         onSubmitHandler(v, name, phone, pass);
        }

      }
    });

  }

  private void onSubmitHandler(final View v, final String name, final String phone, final String pass) {
    UserRegisterModel userRegisterModel = new UserRegisterModel(name, phone, "", pass, "3", "1.14.0");
    Gson gson = new Gson();
    String userString = gson.toJson(userRegisterModel);

    Call<JsonResponse<SignUpResponse>> call = apiService.createUser(userString);
    call.enqueue(new Callback<JsonResponse<SignUpResponse>>() {
      @Override
      public void onResponse(@NonNull Call<JsonResponse<SignUpResponse>> call, @NonNull Response<JsonResponse<SignUpResponse>> response) {
        if (response.body() != null && response.body().getResponseCode().equals("1")) {

          String userId=response.body().getData().get(0).getUserId();
          UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_id), userId);

          UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_number_Keystore),phone);
          UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_password_Keystore),pass);

          startActivity(new Intent(SignUpActivity.this, Dashboard.class));
        }else if (response.body() != null && response.body().getResponseCode().equals("0")) {
          Snackbar snackBar = Snackbar .make(v, response.body().getResponseStatus(), Snackbar.LENGTH_LONG);
          snackBar.show();
        }
      }

      @Override
      public void onFailure(@NonNull Call<JsonResponse<SignUpResponse>> call, @NonNull Throwable t) {
        Snackbar snackBar = Snackbar .make(v, getString(R.string.network_erroe), Snackbar.LENGTH_LONG);
        snackBar.show();
      }
    });

  }

}
