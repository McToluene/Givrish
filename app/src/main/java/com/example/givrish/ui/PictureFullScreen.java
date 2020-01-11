package com.example.givrish.ui;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.givrish.Dashboard;
import com.example.givrish.R;
import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import io.fotoapparat.util.ApiChecksKt;

import static com.example.givrish.database.Constants.CURRENT_USER_PROFILE_PICTURE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFullScreen extends Fragment {

    private CallBackListener listener;
    private ImageView imgFullPicture;
    private ApiEndpointInterface apiService;

    public PictureFullScreen() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBackListener)
            listener = (CallBackListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_picture_full_screen, container, false);

        Toolbar toolbar=view.findViewById(R.id.profile_toolbar);
        toolbar.setTitle("My Picture");

        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackClick(Dashboard.PICTURE_FULLSCREEN_FLAG);
            }
        });

        imgFullPicture=view.findViewById(R.id.imgFullImage);
        if(getArguments() != null){
            String picPath=getArguments().getString("pic");
            Drawable drawable=Drawable.createFromPath(picPath);
            imgFullPicture.setImageDrawable(drawable);
        }
        else {
            loadProfilePic();
        }

        return view;
    }

    private void loadProfilePic() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        String picUrl = "http://givrishapi.divinepagetech.com/profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/";

        try {
            String uri =  picUrl + CURRENT_USER_PROFILE_PICTURE;
            Picasso.get().load(uri).resize(100, 100).noFade().into(imgFullPicture);
        }
        catch (Exception e){
            e.printStackTrace();
            imgFullPicture.setImageResource(R.drawable.defaultprofile);
        }
    }

}
