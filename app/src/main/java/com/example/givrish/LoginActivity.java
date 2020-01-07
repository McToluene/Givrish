package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.givrish.models.LoginResponse;
import com.example.givrish.models.UserData;
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
  private TextInputEditText password;
  private  MaterialButton loginBtn;
  private ProgressBar progressBar;
   private TextView newUserRegistration;
  private String incomingNumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    password = findViewById(R.id.ed_password);
    loginBtn = findViewById(R.id.btn_login);
    progressBar = findViewById(R.id.progressBar2);
    newUserRegistration = findViewById(R.id.new_user_reg);

    progressBar.setVisibility(View.INVISIBLE);
    getNumber();

    newUserRegistration.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,PhoneLoginActivity.class));
        }
    });

    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String pass = password.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);
        try {
          onSubmitHandler(incomingNumber,pass);
        }catch (Exception e){
          Toast.makeText(getApplicationContext(),"Request failed",Toast.LENGTH_LONG).show();
        }
      }
    });

  }

  private void onSubmitHandler( final String incomingNumber,final String pass) {
    UserLoginModel userLogin = new UserLoginModel(incomingNumber,pass);
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
          UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_number_Keystore),incomingNumber);
          UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_password_Keystore),pass);
          startActivity(new Intent(LoginActivity.this, Dashboard.class));
        }
        else if(response.body().getResponseCode().equals("0")){
            Toast.makeText(LoginActivity.this,response.body().getResponseStatus(),Toast.LENGTH_LONG).show();
        }

        else{ Toast.makeText(LoginActivity.this,getString(R.string.network_erroe),Toast.LENGTH_LONG).show();
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
    incomingNumber = getIntent().getStringExtra(PhoneLoginActivity.phoneLoginKey);

  }



}
