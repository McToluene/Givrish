package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.givrish.viewmodel.ProfileViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerifyActivity extends AppCompatActivity {
    public static final String phoneverifyKey = "com.example.givrish.KeyVerify";
    private TextInputEditText edtPhoneNumber;
    private AppCompatEditText edtPhoneCode;
    private String verificationId;
    private MaterialButton btnVerify;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String phonenumber;
    private String resendCodeString = "Resend code";
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private TextView resendCode;
    private String realUserNumber;
    private ProfileViewModel profileViewModel;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       // profileViewModel.saveOriginalState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
//        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
//        profileViewModel = viewModelProvider.get(ProfileViewModel.class);
//        //Todo RestorenInstance state
//        if(savedInstanceState != null){
//            profileViewModel.RestoreOriginalState(savedInstanceState);
//        }

         mAuth = FirebaseAuth.getInstance();
        //Todo
        progressBar = findViewById(R.id.fPassProgressBar);
        edtPhoneNumber = findViewById(R.id.edt_otp_fgPass);
        edtPhoneCode = findViewById(R.id.edt_newPass);
        TextView otpMessage = findViewById(R.id.tv_otpPassMsg);

        resendCode = findViewById(R.id.tv_resend_code_fgPass);
         phonenumber = getIntent().getStringExtra(PhoneLoginActivity.phoneLoginKeyFirebase);
         if(phonenumber != null) {
           edtPhoneNumber.setText(phonenumber);
           otpMessage.setText(buildMessage(phonenumber));
         }

        //Todo

        final String realUserNumberR = "+" + phonenumber;
        realUserNumber = 0 + phonenumber.substring(3);

        sendVerificationCode(realUserNumberR);
         getCoundDown();
          resendCode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              resendCode.setTextColor(getResources().getColor(R.color.colorPrimary));
              resendCode.setEnabled(false);
             resendVerificationCode(realUserNumberR,mResendToken);
             getCoundDown();

          }
      });


        //todo

      btnVerify = findViewById(R.id.btn_verifyPass);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtPhoneCode.getText().toString().trim();
                if (code.isEmpty() || code.length() != 6)
                {
                    Snackbar.make(view,getResources().getString(R.string.valid_number_error),Snackbar.LENGTH_LONG).show();
                    edtPhoneCode.requestFocus();
                    return; } else{
                   // profileViewModel.originalKey = code;
                       //profileViewModel.originalPhoneNumber = realUserNumberR;
                  try { verifyCode(code); }catch (Exception e){
                      Snackbar.make(view,getResources().getString(R.string.valid_number_error),Snackbar.LENGTH_LONG).show();
                  }
                }
            }
        });
    }

        //Todo

    private void resendVerificationCode(String realUserNumberR, PhoneAuthProvider.ForceResendingToken mResendToken) {
         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 realUserNumberR,
                 60,
                 TimeUnit.SECONDS,
                 this,
                 mCallBack,
                 mResendToken
         );
    }


        //Todo
        //A method to verify code that is detected or entered by the user
        private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
       //Allows the user to sign In
        signInWithCredential(credential);
        }

      //  Todo

        private void signInWithCredential(PhoneAuthCredential credential) {
      btnVerify.setEnabled(true);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Intent intent = new Intent(PhoneVerifyActivity.this,SignUpActivity.class);
                   intent.putExtra(PhoneVerifyActivity.phoneverifyKey,realUserNumber);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }else{
                   //progressBar.setVisibility(View.INVISIBLE);
                   Toast.makeText(PhoneVerifyActivity.this,getString(R.string.code_error),Toast.LENGTH_LONG).show();
               }

            }
        });

       }

//        //A method to send verification code


        //Todo

    private void sendVerificationCode(String number){
       progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

        //Todo
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks
           mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
           mResendToken = forceResendingToken;
        }

        //Called when verification is completed
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                //If detected automatically
                edtPhoneCode.setText(code);
                verifyCode(code);
                btnVerify.setEnabled(false);
            }
        }

        //Called when verification fails
        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBar.setVisibility(View.INVISIBLE);
        }

    };


        //Todo
    private String buildMessage(String phonenumber) {
      return String.format("Verification code has been sent to you on your mobile %s please enter it below.", phonenumber);
    }

    private CountDownTimer getCoundDown(){
      return new CountDownTimer(40000, 1000) {
        public void onTick(long millisUntilFinished) {
          resendCode.setText(resendCodeString + " " + millisUntilFinished / 1000);
        }

        public void onFinish() {
          resendCode.setText(resendCodeString);
          resendCode.setEnabled(true);
        }
      }.start();

    }

}
