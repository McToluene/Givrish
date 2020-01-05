package com.example.givrish.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.givrish.R;
import com.example.givrish.database.Constants;
import com.example.givrish.interfaces.ListCallBackEvent;
import com.example.givrish.models.AddItemResponse;
import com.example.givrish.models.AddItemResponseData;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.GetUserItemResponse;
import com.example.givrish.models.ItemModel;
import com.example.givrish.models.ProductModel;
import com.example.givrish.models.ProfileAdapter;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.viewmodel.ListViewModel;
import com.example.givrish.viewmodel.ProfileViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.givrish.database.Constants.*;

public class ProfileFragment extends Fragment implements View.OnClickListener{

  private ProfileViewModel mViewModel;

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private ProfileAdapter myAdapter;

  private ArrayList<String> listString;

  private Button btnEditProf;
  private TextView txtUserNameProfile;

  private ApiEndpointInterface apiService;
  private List<AllItemsResponseData> items;

  private CircleImageView imgProfile;
    private ListCallBackEvent listCallBackEvent;

    @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view=inflater.inflate(R.layout.profile_fragment, container, false);

    Toolbar toolbar=view.findViewById(R.id.profile_toolbar);
    toolbar.setTitle("My Profile");

    if(getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
    }


    listString = ProductModel.getAllTitle();

    recyclerView = view.findViewById(R.id.recyclerMyGiftOut);
    recyclerView.setHasFixedSize(true);

    layoutManager = new LinearLayoutManager(this.getContext());
    recyclerView.setLayoutManager(layoutManager);

    myAdapter = new ProfileAdapter(listString, getContext());
    recyclerView.setAdapter(myAdapter);

    btnEditProf=view.findViewById(R.id.btnEditProfile);
    btnEditProf.setOnClickListener(this);

        setHasOptionsMenu(false);

    txtUserNameProfile=view.findViewById(R.id.id_username);
    txtUserNameProfile.setText(CURRENT_USER_FULLNAME);

    imgProfile=view.findViewById(R.id.profile_image);


    loadProfilePicture();

    return view;
  }

    private void getAllItems() {

        ItemModel itemModel=new ItemModel(CURRENT_USER_ID);
        Gson gson = new Gson();
        final String id = gson.toJson(itemModel);

        Call<List<GetUserItemResponse>> call = apiService.getUserItem(id);
        call.enqueue(new Callback<List<GetUserItemResponse>>() {
            @Override
            public void onResponse(Call<List<GetUserItemResponse>> call, Response<List<GetUserItemResponse>> response) {
                Log.i("RES", response.body().get(0).toString());
                if (response.body() != null && response.body().get(0).getResponseCode().equals("1")) {
                    ItemModel data = response.body().get(0).getData().get(0);
                    String dd = data.getItem_address();
                }
                else {
                    Toast.makeText(getContext(), "No Item added ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetUserItemResponse>> call, Throwable t) {
                t.toString();
            }
        });


    }

    Bitmap theImage;

  @Override
  public void onResume() {
    super.onResume();
    if(ProfileEditFragment.returnValue.equals(null) || ProfileEditFragment.returnValue.get(0).isEmpty())
    loadProfilePicture();
    else
      theImage = BitmapFactory.decodeFile(ProfileEditFragment.returnValue.get(0));
    imgProfile.setImageBitmap(theImage);
  }

  private void loadProfilePicture() {
      apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
      String picUrl = "http://givrishapi.divinepagetech.com/profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/";

      try {
          String uri =  picUrl + CURRENT_USER_PROFILE_PICTURE;
          Picasso.get().load(uri).resize(100, 100).noFade().into(imgProfile);
      }
      catch (Exception e){
          e.printStackTrace();
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
      case R.id.btnEditProfile:
        ProfileEditFragment editFragment=new ProfileEditFragment();
        Bundle bundle=new Bundle();
        bundle.putString("image", CURRENT_USER_PROFILE_PICTURE);
        loadFragment(editFragment, "1");
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
}
