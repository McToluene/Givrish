package com.example.givrish;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordResetFragment extends DialogFragment {
    private static final String server = "myReset";
    private static final String firebase = "mine";
    private String numToFirebase;
    private String numToserver;
    private ProgressBar progressBar;
    private TextView otpPassMessage;
    private TextView resendOTP;
    private  PhoneAuthProvider.ForceResendingToken mResendToken;
    private TextInputEditText txtOtp;
    private TextInputEditText txtNewPas;
    private MaterialButton materialButton;
    private String verificationId;
    private FirebaseAuth mAuth;
    private String resendCodeString = "Resend code";
    private int theMAn;
    private final  int yet = 1;
    private final int notYet = 0;


    public PasswordResetFragment() {
        // Required empty public constructor
    }

    public static PasswordResetFragment newInstance(String toServer,String toFireBase) {
        Bundle args = new Bundle();
        args.putString(server,toServer);
        args.putString(firebase,toFireBase);
        PasswordResetFragment fragment = new PasswordResetFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth =FirebaseAuth.getInstance();

       // sendVerificationCode(numToFirebase);
        getCountDown();

        //setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    //Todo 2

    private void sendVerificationCode(String numToFirebase) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numToFirebase,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack

        );

    }

    //todo 2


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
              mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        String code = phoneAuthCredential.getSmsCode();
         if(code != null){
        //if detected automatically
        txtOtp.setText(code);
         verifyCode(code);
       }
        }


    //todo 3


        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            mResendToken = forceResendingToken;

        }
    };



    //todo 4

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
       signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
     materialButton.setEnabled(true);
     mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               txtNewPas.setEnabled(true);
               theMAn = yet;
               Toast.makeText(getContext(), "You can now enter a new Password", Toast.LENGTH_SHORT).show();


           }else{ Toast.makeText(getContext(), getString(R.string.code_error), Toast.LENGTH_SHORT).show(); }

         }
     });

    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width,height);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_reset, container, false);
        txtOtp = view.findViewById(R.id.edt_otp_fgPass);
        txtNewPas = view.findViewById(R.id.edt_newPass);
        materialButton = view.findViewById(R.id.btn_verifyPass);
        progressBar = view.findViewById(R.id.fPassProgressBar);
        otpPassMessage = view.findViewById(R.id.tv_otpPassMsg);
        resendOTP = view.findViewById(R.id.tv_resend_code);



        if(getArguments() != null){
            numToserver = getArguments().getString(server);
            numToFirebase = getArguments().getString(firebase);
            Toast.makeText(getContext(), numToFirebase,Toast.LENGTH_LONG).show();
            String now = "+" + 234 + "xxxxxxx" + numToFirebase.substring(10);
            otpPassMessage.setText(buildMessage(now));
        }
        txtNewPas.setEnabled(false);
        //todo 7


       resendOTP.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               resendOTP.setTextColor(getResources().getColor(R.color.colorPrimary));
               resendOTP.setEnabled(false);
              resendVerificationCode(numToFirebase,mResendToken);
               getCountDown();
           }
       });



        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theMAn =notYet;
            String code = txtOtp.getText().toString().trim();
             if(code.isEmpty() || code.length() != 6){
                 Snackbar.make(v,getResources().getString(R.string.valid_number_error),Snackbar.LENGTH_LONG).show();
                 txtOtp.requestFocus();
                 return; }else{ try { verifyCode(code); }catch (Exception e){
                     Snackbar.make(v,getResources().getString(R.string.valid_number_error),Snackbar.LENGTH_LONG).show(); }
                 if(theMAn == yet){
                     //todo call the api here....



                 }
             }
            }
        });
        return view;
    }
    //todo 5



    private void resendVerificationCode(String numToFirebase, PhoneAuthProvider.ForceResendingToken mResendToken) {
         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 numToFirebase,
                 60,
                 TimeUnit.SECONDS,
                 getActivity(),
                 mCallBack,
                 mResendToken
         );
    }

    private String buildMessage(String numToserver){
        return String.format("Verification code has been sent to on your mobile %s please enter it below",numToserver);
    }

    private CountDownTimer getCountDown(){
       return new CountDownTimer(40000,1000) {
           @Override
           public void onTick(long millisUntilFinished) {
               resendOTP.setText(resendCodeString + " " + millisUntilFinished/1000);
           }

           @Override
           public void onFinish() {
             resendOTP.setText(resendCodeString);
             resendOTP.setEnabled(true);
           }
       }.start();
    }

}
