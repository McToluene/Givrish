package com.example.givrish;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment implements View.OnClickListener{

    private EditText edtUsername;
    private TextView btnChangePix;
    private Button btnSave;

    private Options options;
    private ArrayList<String> returnValue;
    private static final int PIX_REQUEST_CODE=111;
    private CircleImageView imgProfile;

    public ProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_edit_fragment, container, false);

        Toolbar toolbar=view.findViewById(R.id.edit_profile_toolbar);
        toolbar.setTitle("Edit Profile");

        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
        }

        edtUsername=view.findViewById(R.id.edtProfileNameEditProfile);
        edtUsername.setText(getArguments().getString("username"));


        returnValue = new ArrayList<>();
        imgProfile=view.findViewById(R.id.profile_imageEdit);
        btnChangePix=view.findViewById(R.id.tvChangeProfilePix);
        btnChangePix.setOnClickListener(this);

        btnSave=view.findViewById(R.id.btnSaveProfileEdit);
        btnSave.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvChangeProfilePix:
                options = Options.init()
                        .setRequestCode(PIX_REQUEST_CODE)
                        .setCount(1)
                        .setFrontfacing(false)
                        .setImageQuality(ImageQuality.REGULAR)
                        .setPreSelectedUrls(returnValue)
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                        .setPath("/pix/images");

                Pix.start(getActivity(), options);
                break;
            case R.id.btnSaveProfileEdit:

                Toast.makeText(getContext(), "need to be save to API", Toast.LENGTH_LONG).show();
                //API to save into

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PIX_REQUEST_CODE) {
            if (data != null) {
                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                if (returnValue != null) {
                    Bitmap theImage = BitmapFactory.decodeFile(returnValue.get(0));
                    imgProfile.setImageBitmap(theImage);
                }
            }
        }
    }
}
