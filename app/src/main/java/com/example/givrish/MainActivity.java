package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.givrish.ui.ProfileEditFragment;
import com.example.givrish.ui.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(retreiveUserLoginDetails()){
           startActivity(new Intent(MainActivity.this,Dashboard.class));
        }


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
    //Todo

    private boolean retreiveUserLoginDetails(){
        String loginPhoneNumber = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_number_Keystore));
        String loginPassword = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_password_Keystore));
        return !TextUtils.isEmpty(loginPhoneNumber) && !TextUtils.isEmpty(loginPassword);
    }

}



