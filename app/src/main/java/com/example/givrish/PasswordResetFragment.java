package com.example.givrish;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordResetFragment extends Fragment {


    public PasswordResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_reset, container, false);
        TextInputEditText txtOtp = view.findViewById(R.id.edt_otp_fgPass);
        TextInputEditText txtNewPas  = view.findViewById(R.id.edt_newPass);
        MaterialButton materialButton = view.findViewById(R.id.btn_verifyPass);
        ProgressBar progressBar = view.findViewById(R.id.fPassProgressBar);
        TextView otpPassMessage = view.findViewById(R.id.tv_otpPassMsg);
        TextView resendOTP = view.findViewById(R.id.tv_resend_code_fgPass);

        return view;
    }

}
