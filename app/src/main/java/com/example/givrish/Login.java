package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.UserLoginModel;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextInputEditText phoneNumber;
    private TextInputEditText password;
    private MaterialButton login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber = findViewById(R.id.edt_phoneNumber);
        password = findViewById(R.id.ed_password);
        login = findViewById(R.id.btn_login);
        getNumber();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String number =  phoneNumber.getText().toString();
              String pass = password.getText().toString();
              progressBar.setVisibility(View.VISIBLE);
              login.setEnabled(false);
              onSubmitHandler(number, pass);
            }
        });

    }

    private void onSubmitHandler(String number, String pass) {
        UserLoginModel userLogin = new UserLoginModel(number, pass);
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        Gson gson = new Gson();
        String loginString = gson.toJson(userLogin);
        Call<AuthResponseDto> call = apiService.login(loginString);
        call.enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                if(response.body().getResponseCode().equals("1")){
                    startActivity(new Intent(Login.this, Dashboard.class));
                }
            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                login.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getNumber() {
        String incomingNumber = getIntent().getStringExtra(PhoneLoginActivity.phoneLoginKey);
        if (incomingNumber != null) phoneNumber.setText(incomingNumber);
    }
}
