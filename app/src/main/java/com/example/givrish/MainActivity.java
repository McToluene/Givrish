package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        if(SignUpActivity.monitoringUserSignupFlag != false && PhoneLoginActivity.monitoringUserLVFlag == true && PhoneVerifyActivity.monitoringUserVerificationFlag == true){


        }else if(SignUpActivity.monitoringUserSignupFlag != true && PhoneVerifyActivity.monitoringUserVerificationFlag == true){ }


         if(retreiveUserRegistrationDetails() == true && retreiveUserLoginDetails() != true){
            startActivity(new Intent(MainActivity.this, LoginActivity.class)); }
        else if(FirebaseAuth.getInstance().getCurrentUser() != null && retreiveUserRegistrationDetails() != true && retreiveUserLoginDetails() != true){
            startActivity(new Intent(MainActivity.this, SignUpActivity.class)); }
        else if(retreiveUserLoginDetails() == true){
            startActivity(new Intent(MainActivity.this, Dashboard.class)); }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PhoneLoginActivity.class));
            }
        });
    }

    private boolean retreiveUserRegistrationDetails(){
        UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_fullname));
        UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_number_Keystore));
        UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_password_Keystore));
        return true;
    }



    private boolean retreiveUserLoginDetails(){
        UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_number_Keystore));
        UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_password_Keystore));
        return true;
    }




}



