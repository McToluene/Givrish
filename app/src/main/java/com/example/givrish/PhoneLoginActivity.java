package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.givrish.models.UserRegisterModel;
import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PhoneLoginActivity extends AppCompatActivity {
  private MaterialButton btnCheck;
  private TextInputEditText edtPhoneNumberLogin;
  public static final String phoneLoginKey = "com.example.givrish.phoneActivityKey";
  public static final String phoneLoginKeyFirebase = "com.example.givrish.phoneActivityKeyFireBase";
  private ApiEndpointInterface apiService;
  CountryCodePicker cpp;
  private String registeredUser;
  private String registeringUserToFirebase;
   ProgressDialog progressDialog;
private Executor executor = Executors.newSingleThreadExecutor();

  @Override
  protected void onStart() {
    super.onStart();

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_phone_login);

    edtPhoneNumberLogin = findViewById(R.id.edt_phoneNumber);
    btnCheck = findViewById(R.id.btn_check);
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
    cpp = findViewById(R.id.ccp);
    cpp.registerCarrierNumberEditText(edtPhoneNumberLogin);
    cpp.setFullNumber(edtPhoneNumberLogin.getText().toString().trim());
    btnCheck.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        registeringUserToFirebase = cpp.getFullNumber();
        registeredUser = 0 + cpp.getFullNumber().substring(3);


        if(cpp.isValidFullNumber() != true){
            Snackbar.make(view, "Enter valid number", Snackbar.LENGTH_LONG).show();edtPhoneNumberLogin.requestFocus();return; }
        else if (!isConnectionActive()) {
          Snackbar.make(view, getString(R.string.connection_error), Snackbar.LENGTH_LONG).show(); } else {
          progressDialogMethod();
          executor.execute(new Runnable() {
            @Override
            public void run() {
              try {
                onCheckHandler(view,registeredUser);
                }catch (Exception e){e.printStackTrace();}
            }
          });

        }
      }
    });
  }

    private void progressDialogMethod() {
        progressDialog = new ProgressDialog(PhoneLoginActivity.this,R.style.progressDialogStyle);
        progressDialog.setMessage("Please wait..");
        progressDialog.setTitle("Welcome");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    private void onCheckHandler(final View view, final String phoneNumber) {
    UserRegisterModel userRegisterModelCheck = new UserRegisterModel(phoneNumber, "40:ab:32:10:ao");
    Gson gson = new Gson();
    final String userStringCheck = gson.toJson(userRegisterModelCheck);
    Call<AuthResponseDto> callUser = apiService.checkUser(userStringCheck);
    callUser.enqueue(new Callback<AuthResponseDto>() {
      @Override
      public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
        if (response.body().getResponseCode().equals("1")) {
           Intent intent  = new Intent(getApplicationContext(), LoginActivity.class);
         intent.putExtra(PhoneLoginActivity.phoneLoginKey, registeredUser);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (response.body().getResponseCode().equals("0")) {
          Intent intent = new Intent(getApplicationContext(), PhoneVerifyActivity.class);
            UserDataPreference.getInstance(getApplicationContext()).savePreference(getString(R.string.user_phone_number_Keystore),registeredUser);
            intent.putExtra(PhoneLoginActivity.phoneLoginKeyFirebase, registeringUserToFirebase);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
        }
      }

      @Override
      public void onFailure(Call<AuthResponseDto> call, Throwable t) {
        Toast.makeText(PhoneLoginActivity.this, getString(R.string.network_erroe), Toast.LENGTH_LONG);
      }
    });

  }

  private boolean isConnectionActive() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }


}