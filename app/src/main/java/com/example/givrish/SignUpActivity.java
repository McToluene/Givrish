package com.example.givrish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText edtPhoneNoSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtPhoneNoSignUp = findViewById(R.id.edt_phone_sign_up);

        String phone = getIntent().getStringExtra(PhoneVerifyActivity.phoneverifyKey);
        edtPhoneNoSignUp.setText(phone);

    }
}
