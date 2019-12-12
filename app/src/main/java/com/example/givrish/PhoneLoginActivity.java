package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class PhoneLoginActivity extends AppCompatActivity {
    private AppCompatButton btnLogin;
    private TextInputEditText edtPhoneNumberLogin;
    public static final String phoneLoginKey = "com.example.givrish.phoneActivityKey";

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
                    String phoneNumber = "+" + 234 + number;
                    Intent intent = new Intent(PhoneLoginActivity.this, PhoneVerifyActivity.class);
                    intent.putExtra(PhoneLoginActivity.phoneLoginKey,phoneNumber);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean isConnectionActive() {
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
