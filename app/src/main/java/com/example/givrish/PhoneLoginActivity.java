package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.example.givrish.models.User;
import com.example.givrish.models.UserRegisterResponseDto;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {
    private AppCompatButton btnLogin;
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
        btnLogin = findViewById(R.id.btn_phoneLogin);
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
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
                    OnSubmitCheckhandler(view, phoneNumber);
                }
            }
        });
    }

    private void OnSubmitCheckhandler(final View view, final String phoneNumber) {
        User userCheck = new User(phoneNumber,"40:ab:32:10:ao");
        Gson gson = new Gson();
         final String userStringcheck = gson.toJson(userCheck);

        Call<UserRegisterResponseDto> callUser = apiService.checkUser(userStringcheck);
        callUser.enqueue(new Callback<UserRegisterResponseDto>() {
            @Override
            public void onResponse(Call<UserRegisterResponseDto> call, Response<UserRegisterResponseDto> response) {
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
            public void onFailure(Call<UserRegisterResponseDto> call, Throwable t) {

            }
        });

    }
    private boolean isConnectionActive() {
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
