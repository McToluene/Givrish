package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {
  private MaterialButton btnCheck;
  private TextInputEditText edtPhoneNumberLogin;
  public static final String phoneLoginKey = "com.example.givrish.phoneActivityKey";
  private ApiEndpointInterface apiService;
  private String phoneNumber;
  //
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            Intent intent = new Intent(this,Login.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_phone_login);

    edtPhoneNumberLogin = findViewById(R.id.edt_phoneNumber);
    btnCheck = findViewById(R.id.btn_check);
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);

    btnCheck.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String number = edtPhoneNumberLogin.getText().toString().trim();
        if(number.isEmpty() || number.length() > 11){
          edtPhoneNumberLogin.setError(getString(R.string.error_message));
          edtPhoneNumberLogin.requestFocus();
          return;
        }else if(!isConnectionActive()){
          Snackbar.make(view,getString(R.string.connection_error),Snackbar.LENGTH_LONG).show();
        }
        else{
          phoneNumber = "+" + 234 + number;
          onCheckHandler(view, phoneNumber);
        }
      }
    });
  }

  private void onCheckHandler(final View view, final String phoneNumber) {
    UserRegisterModel userRegisterModelCheck = new UserRegisterModel(phoneNumber,"40:ab:32:10:ao");
    Gson gson = new Gson();
    final String userStringCheck = gson.toJson(userRegisterModelCheck);
  Log.i("BTN", "cLICKED");
    Call<AuthResponseDto> callUser = apiService.checkUser(userStringCheck);
    callUser.enqueue(new Callback<AuthResponseDto>() {
      @Override
      public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
        Log.i("SUccess", response.toString());
        if(response.body().getResponseCode().equals("1")){
          Intent intent = new Intent(getApplicationContext(),Login.class);
          intent.putExtra(PhoneLoginActivity.phoneLoginKey,phoneNumber);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
        }else if(response.body().getResponseCode().equals("0")){
          Intent intent = new Intent(getApplicationContext(),PhoneVerifyActivity.class);
          intent.putExtra(PhoneLoginActivity.phoneLoginKey,phoneNumber);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
        }
      }
      @Override
      public void onFailure(Call<AuthResponseDto> call, Throwable t) {
        Log.i("Error", t.getMessage());
        Toast.makeText(PhoneLoginActivity.this, t.getMessage(), Toast.LENGTH_LONG);
      }
    });

  }
  private boolean isConnectionActive() {
    ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }
}
