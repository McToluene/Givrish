package com.example.givrish.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.FileUtils;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.givrish.Dashboard;
import com.example.givrish.R;
import com.example.givrish.database.Constants;
import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.models.AddItemResponse;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.AuthResponseDto;
import com.example.givrish.models.ProfileEditResponse;
import com.example.givrish.models.UserData;
import com.example.givrish.models.UserId;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.givrish.database.Constants.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment implements View.OnClickListener{

    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPhoneNo;

    private TextView btnChangePix;
    private Button btnSave;
    Bitmap theImage;


    public static ArrayList<String> returnValue = new ArrayList<>();
    private static final int PIX_REQUEST_CODE=111;
    private CircleImageView imgProfile;

    private ApiEndpointInterface apiService;
    ProfileFragment fragment;
    String username;
    String pic;

    private CallBackListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBackListener)
            listener = (CallBackListener) context;
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackClick(Dashboard.PROFILE_EDIT_FLAG);
            }
        });
        edtUsername=view.findViewById(R.id.edtProfileNameEditProfile);
        edtUsername.setText(CURRENT_USER_FULLNAME);

        edtPhoneNo=view.findViewById(R.id.edtPhoneNoEditProfile);
        edtPhoneNo.setText(CURRENT_USER_PHONE_NUMBER);

        edtEmail=view.findViewById(R.id.edtEmailEditProfile);
        edtEmail.setText(CURRENT_USER_EMAIL);


        imgProfile=view.findViewById(R.id.profile_imageEdit);
        btnChangePix=view.findViewById(R.id.tvChangeProfilePix);
        btnChangePix.setOnClickListener(this);

        btnSave=view.findViewById(R.id.btnSaveProfileEdit);
        btnSave.setOnClickListener(this);

        if(getArguments() != null){
            pic=getArguments().getString("pic");
            Drawable theImage = Drawable.createFromPath(pic);
            imgProfile.setImageDrawable(theImage);
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
            Picasso.get().load(uri).resize(100, 100).noFade().into(imgProfile);
        }
        catch (Exception e){
            e.printStackTrace();
            imgProfile.setImageResource(R.drawable.defaultprofile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvChangeProfilePix:
                Pix.start(getActivity(), Options.init().setRequestCode(PIX_REQUEST_CODE));
                break;

            case R.id.btnSaveProfileEdit:
                if(!returnValue.isEmpty()) {
                    uploadImage(returnValue.get(0));
                }
                username=edtUsername.getText().toString().trim();
                if(!(username.equals(CURRENT_USER_FULLNAME.trim()))) {
                    updateFullname(username);
                }
                if(username.equals(CURRENT_USER_FULLNAME.trim()) && returnValue.isEmpty()) {
                    Toast.makeText(getContext(), "Notice: You did not change any detail.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void updateFullname(final String username) {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        UserId userId=new UserId(CURRENT_USER_ID, username);
        Gson gson=new Gson();
        String user=gson.toJson(userId);

        Call<AuthResponseDto> call = apiService.updateUserDetail(user);
        call.enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                CURRENT_USER_FULLNAME=username;
                Toast.makeText(getContext(), "Successfully updated username", Toast.LENGTH_LONG).show();
                loadProfile();
            }

            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to update the details", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadProfile() {
        ListFragment fragment=new ListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("pic", CURRENT_USER_PROFILE_PICTURE);
        fragment.setArguments(bundle);
        loadFragment(fragment, Dashboard.LIST_ITEM_FRAGMENT_FLAG);
    }

    private void uploadImage(final String path) {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
            final File file = new File(path);
            //Request body
            final RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            try {
                Call<List<ProfileEditResponse>> call = apiService.updateProfilePix(part, description, CURRENT_USER_ID);
                call.enqueue(new Callback<List<ProfileEditResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProfileEditResponse>> call, Response<List<ProfileEditResponse>> response) {
                        CURRENT_USER_PROFILE_PICTURE = returnValue.get(0);
                        returnValue.clear();
                        Toast.makeText(getContext(), "Successfully updated picture", Toast.LENGTH_LONG).show();
                        if (username.trim().equals(CURRENT_USER_FULLNAME.trim())) {
                            loadProfile();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProfileEditResponse>> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to save the image", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction;
        if (getActivity() != null) {
            transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.dashboard_layout, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PIX_REQUEST_CODE) {
            if (data != null) {
                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                if (returnValue != null) {
                    theImage = BitmapFactory.decodeFile(returnValue.get(0));
                    imgProfile.setImageBitmap(theImage);
                }
            }
        }
        else {
            if(getArguments() != null){
                pic=getArguments().getString("pic");
                Drawable theImage = Drawable.createFromPath(pic);
                imgProfile.setImageDrawable(theImage);
            }
            else {
                loadProfilePic();
            }
        }
    }
}
