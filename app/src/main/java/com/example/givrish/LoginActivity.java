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
import com.google.android.material.snackbar.Snackbar;
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
  private String incomingNumber;
    private TextView forgotPassword;
    private  PasswordResetFragment passwordResetFragment;
    private String toFireBase;

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    password = findViewById(R.id.ed_password);
    loginBtn = findViewById(R.id.btn_login);
    progressBar = findViewById(R.id.progressBar2);
        forgotPassword = findViewById(R.id.forgot_pass);

    progressBar.setVisibility(View.INVISIBLE);
    getNumber();

    forgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             passwordResetFragment = PasswordResetFragment.newInstance(incomingNumber,toFireBase);
            passwordResetFragment.show(getSupportFragmentManager(),"forgotPassword");
        }
    });

    loginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String pass = password.getText().toString();
          if(pass.isEmpty()){
              Snackbar.make(v,"Enter password",Snackbar.LENGTH_LONG).show();
          }else {
              progressBar.setVisibility(View.VISIBLE);
              try {
                  onSubmitHandler(incomingNumber, pass);
              } catch (Exception e) {
                  Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_LONG).show();
              }
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

            String userId=response.body().getData().get(0).getUser_id();
            String userFullName = response.body().getData().get(0).getFullname();
            String userEmail = response.body().getData().get(0).getEmail();
            String userPicture = response.body().getData().get(0).getProfile_picture();

            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_number_Keystore),incomingNumber);
          UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_password_Keystore),pass);

            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_id), userId);
            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_fullname_Keystore), userFullName);
            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_email_Keystore), userEmail);
            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_picture), userPicture);
            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.PicAvailable), "false");

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
      toFireBase = getIntent().getStringExtra("forgotPassword");

  }



}
