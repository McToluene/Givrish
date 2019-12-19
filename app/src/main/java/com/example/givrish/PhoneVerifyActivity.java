package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.UserRegisterModel;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneVerifyActivity extends AppCompatActivity {
      public static final String phoneverifyKey = "com.example.givrish.KeyVerify";
      private TextInputEditText edtPhoneNumber;
      private AppCompatEditText edtPhoneCode;
      private String verificationId;
      private MaterialButton btnVerify;
      private FirebaseAuth mAuth;
      private ProgressBar progressBar;
      private String phonenumber;
      private String resendCodeString = "Resend code ";
  private TextView resendCode;
  private String realUserNumber;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        edtPhoneNumber = findViewById(R.id.ed_phoneNumber);
        edtPhoneCode = findViewById(R.id.edt_phoneCode);
        TextView otpMessage = findViewById(R.id.tv_otpMessage);
        resendCode = findViewById(R.id.tv_resend_code);


         phonenumber = getIntent().getStringExtra(PhoneLoginActivity.phoneLoginKeyFirebase);
         if(phonenumber != null) {
           edtPhoneNumber.setText(phonenumber);
           otpMessage.setText(buildMessage(phonenumber));
         }

         String realUserNumberR = "+" + phonenumber;
         realUserNumber = 0 + phonenumber.substring(3);
         sendVerificationCode(realUserNumberR);
         getCoundDown();

        btnVerify = findViewById(R.id.btn_phoneVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtPhoneCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edtPhoneCode.setError("Enter code...");
                    edtPhoneCode.requestFocus();
                    return;
                }else{ btnVerify.setEnabled(false);verifyCode(code);}
            }
        });
    }


        //A method to verify code that is detected or entered by the user
        private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
       //Allows the user to sign In
        signInWithCredential(credential);
        }
//
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
                   progressBar.setVisibility(View.INVISIBLE);
                   Toast.makeText(PhoneVerifyActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
               }

            }
        });

       }

//        //A method to send verification code
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

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
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
            Toast.makeText(PhoneVerifyActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();}

    };

    private String buildMessage(String phonenumber) {
      return String.format("Verification code has been sent to you on your mobile %s please enter it below.", phonenumber);
    }

    private CountDownTimer getCoundDown(){
      return new CountDownTimer(60000, 1000) {
        public void onTick(long millisUntilFinished) {
          resendCode.setText(resendCodeString + millisUntilFinished / 1000);

        }

        public void onFinish() {
          resendCode.setText(resendCodeString);
          resendCode.setEnabled(true);
        }
      }.start();
    }
}
