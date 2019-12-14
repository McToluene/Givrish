package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.givrish.models.AuthResponseDto;
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
    MaterialButton signUp = findViewById(R.id.btn_signUp);

    String phone = getIntent().getStringExtra(PhoneVerifyActivity.phoneverifyKey);
    if(phone != null) number.setText(phone);

    signUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String name = fullName.getText().toString();
        String phone = number.getText().toString();
        String pass = password.getText().toString();
        onSubmitHandler(v, name, phone, pass);
      }
    });

  }

  private void onSubmitHandler( final View v, String name, String phone, String pass) {
    UserRegisterModel userRegisterModel = new UserRegisterModel(name, phone, pass);
    Gson gson = new Gson();
    String userString = gson.toJson(userRegisterModel);

    Call<AuthResponseDto> call = apiService.createUser(userString);
    call.enqueue(new Callback<AuthResponseDto>() {
      @Override
      public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
        if (response.body().getResponseCode().equals("1")) startActivity(new Intent(SignUpActivity.this, Dashboard.class));

      }

      @Override
      public void onFailure(Call<AuthResponseDto> call, Throwable t) {
        Snackbar snackBar = Snackbar .make(v, "Please try again", Snackbar.LENGTH_LONG);
        snackBar.show();
      }
    });

  }

}
