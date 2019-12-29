package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        if (PhoneLoginActivity.monitoringUserLVFlag == true && PhoneVerifyActivity.monitoringUserVerificationFlag == true) {
            //Todo goto Dashboard Activity
           // startActivity(new Intent(MainActivity.this, Dashboard.class));
            if (PhoneLoginActivity.monitoringUserLVFlag == false && PhoneVerifyActivity.monitoringUserVerificationFlag == false) {
                //Todo goto signUp Activity
                //startActivity(new Intent(MainActivity.this, SignUpActivity.class));

                if (FirebaseAuth.getInstance().getCurrentUser() != null && SignUpActivity.monitoringUserSignupFlag == false) {
                    // Todo goto SignUp page
                }

            }


        }


        if (FirebaseAuth.getInstance().getCurrentUser() != null && retreiveUserRegistrationDetails()) {
            startActivity(new Intent(MainActivity.this, Dashboard.class));
        } else if (FirebaseAuth.getInstance().getCurrentUser() != null && !retreiveUserRegistrationDetails() ) {
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        }


//        else if (retreiveUserLoginDetails() == true) {
//            startActivity(new Intent(MainActivity.this, Dashboard.class));
//        }

//        UserDataPreference.getInstance(MainActivity.this).clearPreference(getString(R.string.user_fullname_Keystore));
//        UserDataPreference.getInstance(MainActivity.this).clearPreference(getString(R.string.user_phone_number_Keystore));
//        UserDataPreference.getInstance(MainActivity.this).clearPreference(getString(R.string.user_phone_password_Keystore));

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
        String fullname = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_fullname_Keystore));
        String phoneNumber = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_number_Keystore));
        String password = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_password_Keystore));
        return !TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(password);
    }

    private boolean retreiveUserLoginDetails(){
        String loginPhoneNumber = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_number_Keystore));
        String loginPhonePassword = UserDataPreference.getInstance(MainActivity.this).retrievePreference(getString(R.string.user_phone_password_Keystore));
        return !TextUtils.isEmpty(loginPhoneNumber) && !TextUtils.isEmpty(loginPhonePassword);
    }
}



