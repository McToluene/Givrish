package com.example.givrish;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment implements View.OnClickListener{

    private EditText edtUsername;
    private TextView btnChangePix;
    private Options options;
    private ArrayList<String> returnValue = new ArrayList<>();

    public ProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_edit_fragment, container, false);

       /* toolbar.setTitle("Add Item");

        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
        }*/

        edtUsername=view.findViewById(R.id.edtProfileNameEditProfile);
        edtUsername.setText(getArguments().getString("username"));

        btnChangePix=view.findViewById(R.id.tvChangeProfilePix);
        btnChangePix.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvChangeProfilePix:
                options = Options.init()
                        .setRequestCode(100)
                        .setCount(3)
                        .setFrontfacing(false)
                        .setImageQuality(ImageQuality.REGULAR)
                        .setPreSelectedUrls(returnValue)
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                        .setPath("/pix/images");

                Pix.start(getActivity(), options);
                break;
        }
    }
}
