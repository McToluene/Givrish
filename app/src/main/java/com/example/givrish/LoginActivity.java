package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.givrish.models.LoginResponse;
import com.example.givrish.models.UserLoginModel;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
  private TextInputEditText phoneNumber;
  private TextInputEditText password;
  private  MaterialButton loginBtn;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    phoneNumber = findViewById(R.id.edt_phoneNumber);
    password = findViewById(R.id.ed_password);
    loginBtn = findViewById(R.id.btn_login);
    progressBar = findViewById(R.id.progressBar2);
    progressBar.setVisibility(View.INVISIBLE);
    getNumber();

    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String number =  phoneNumber.getText().toString();
        String pass = password.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);
        onSubmitHandler(number, pass);
      }
    });

  }

  private void onSubmitHandler(String number, String pass) {
    UserLoginModel userLogin = new UserLoginModel(number, pass);
    ApiEndpointInterface  apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);

    Gson gson = new GsonBuilder().create();
    String login = gson.toJson(userLogin);
    Log.i("USER", login);
    Call<LoginResponse> call = apiService.login(login);
    call.enqueue(new Callback<LoginResponse>() {
      @Override
      public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        Log.i("RES", response.body().getResponseStatus());
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        if(response.body().getResponseCode().equals("1")){
          startActivity(new Intent(LoginActivity.this, Dashboard.class));
        }
      }

      @Override
      public void onFailure(Call<LoginResponse> call, Throwable t) {
        Log.i("ERROR", t.toString());
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
      }
    });
  }

  private void getNumber() {
    String incomingNumber = getIntent().getStringExtra(PhoneVerifyActivity.phoneverifyKey);
    if (incomingNumber != null) phoneNumber.setText(incomingNumber);
  }
}
