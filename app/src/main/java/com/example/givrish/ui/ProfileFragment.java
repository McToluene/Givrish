package com.example.givrish.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.givrish.Dashboard;
import com.example.givrish.R;
import com.example.givrish.database.Constants;
import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.interfaces.IUserItemCallBackEvent;


import com.example.givrish.PhoneLoginActivity;

import com.example.givrish.UserDataPreference;


import com.example.givrish.models.ApiKey;
import com.example.givrish.models.GetUserItemResponse;
import com.example.givrish.models.GetUserItemResponseData;

import com.example.givrish.models.UserProfileAdapter;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;

import com.example.givrish.viewmodel.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.util.List;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.givrish.database.Constants.*;

public class ProfileFragment extends Fragment implements View.OnClickListener, IUserItemCallBackEvent{

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;

  private TextView txtUserNameProfile;
    private CircleImageView imgProfile;
    private ImageView imgProfile2;
    public static TextView txtItemCount;


  private ApiEndpointInterface apiService;
  private GetUserItemResponseData items;

    private IUserItemCallBackEvent listCallBackEvent;
    private ProfileViewModel mViewModel;
    private UserProfileAdapter profileAdapter;

    private CallBackListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBackListener)
            listener = (CallBackListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        setHasOptionsMenu(true);
        listCallBackEvent = this;
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        if(ITEM_COUNT_MORE==0)
        getAllItems();
    }


  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.profile_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                UserDataPreference.getInstance(getContext()).clearPreference();
                startActivity(new Intent(getContext(), PhoneLoginActivity.class));
                break;
            case R.id.menuEditProfile:
                ProfileEditFragment editFragment=new ProfileEditFragment();
                if(CURRENT_USER_PROFILE_PICTURE!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pic", CURRENT_USER_PROFILE_PICTURE);
                    editFragment.setArguments(bundle);
                }
                loadFragment(editFragment, Dashboard.PROFILE_EDIT_FLAG);
                break;
        }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.profile_fragment, container, false);

      Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
      toolbar.setTitle("My Profile");

      if (getActivity() != null) {
          ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
          ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
          ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
      }

      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getContext(), Dashboard.class);
              intent.putExtra("pic", CURRENT_USER_PROFILE_PICTURE);
              startActivity(intent);
          }
      });

      recyclerView = view.findViewById(R.id.recyclerMyGiftOut);
      recyclerView.setHasFixedSize(true);

      txtUserNameProfile = view.findViewById(R.id.id_username);
      txtUserNameProfile.setText(CURRENT_USER_FULLNAME);

      txtItemCount = view.findViewById(R.id.txtItemAdded);

      imgProfile = view.findViewById(R.id.profile_image);
      imgProfile2 = view.findViewById(R.id.profile_image2);
      imgProfile.setOnClickListener(this);

      mViewModel.getItems().observe(this, new Observer<List<GetUserItemResponseData>>() {
          @Override
          public void onChanged(final List<GetUserItemResponseData> getUserItemResponseData) {
              profileAdapter.setUserItemsResponseData(getUserItemResponseData);
          }
      });

        if(getArguments() != null){
            CURRENT_USER_PROFILE_PICTURE=getArguments().getString("pic");
            Drawable theImage = Drawable.createFromPath(CURRENT_USER_PROFILE_PICTURE);
            imgProfile.setImageDrawable(theImage);
            imgProfile2.setImageDrawable(theImage);
        }
        else {
            loadProfilePicture();
        }
        if(PROFILE_PICTURE == false){
            imgProfile.setImageResource(R.drawable.defaultprofile);
            imgProfile2.setImageResource(R.drawable.defaultprofile);
        }
        inflateRecycler();
        return view;
  }

    @Override
    public void onResume() {
        super.onResume();
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        if(ITEM_COUNT_MORE==0) {
            getAllItems();
        }
        inflateRecycler();
    }

    private void inflateRecycler() {
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        profileAdapter = new UserProfileAdapter(this.getContext());
        recyclerView.setAdapter(profileAdapter);
    }

    private void getAllItems() {
        Gson gson = new Gson();
        ApiKey apiKey=new ApiKey("test", CURRENT_USER_ID);
        String apKey=gson.toJson(apiKey);

        Call<GetUserItemResponse> call = apiService.getUserItem(apKey);
        call.enqueue(new Callback<GetUserItemResponse>() {
            @Override
            public void onResponse(Call<GetUserItemResponse> call, Response<GetUserItemResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getResponseCode().equals("1")) {
                         listCallBackEvent.userItemsLoaded(response.body().getData());
                    }
                    else {
                        Toast.makeText(getContext(), "You have add no item", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserItemResponse> call, Throwable t) {
                if (getView() != null)
                    Snackbar.make(getView(), "Please check your network", Snackbar.LENGTH_SHORT)
                            .show();
            }
        });
    }

  private void loadProfilePicture() {
      apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
      String picUrl = "http://givrishapi.divinepagetech.com/profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/";

      try {
          String uri =  picUrl + CURRENT_USER_PROFILE_PICTURE;
          Picasso.get().load(uri).resize(100, 100).noFade().into(imgProfile);
          Picasso.get().load(uri).resize(100, 100).noFade().into(imgProfile2);
          PROFILE_PICTURE =true;
      }
      catch (Exception e){
          e.printStackTrace();
          imgProfile.setImageResource(R.drawable.defaultprofile);
          imgProfile2.setImageResource(R.drawable.defaultprofile);
      }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
        case R.id.profile_image:
            PictureFullScreen pictureFullScreen =new PictureFullScreen();
            if(CURRENT_USER_PROFILE_PICTURE!=null) {
                Bundle  bundle = new Bundle();
                bundle.putString("pic", CURRENT_USER_PROFILE_PICTURE);
                pictureFullScreen.setArguments(bundle);
            }
            loadFragment(pictureFullScreen, Dashboard.PICTURE_FULLSCREEN_FLAG);
            break;
    }
  }

//TODO send the string of the picture to the edit fragment
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
    public void userItemsLoaded(List<GetUserItemResponseData> items) {
        mViewModel.insertAllItems(getNewItems(items));
        profileAdapter.setUserItemsResponseData(items);
    }

    private List<GetUserItemResponseData> getNewItems(List<GetUserItemResponseData> items) {
        if (items.size() > 20){
            int lastIndex = items.size();
            int fromIndex = lastIndex - 20;
            return items.subList(fromIndex, lastIndex);
        }
        return items;
    }

}
