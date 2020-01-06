package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.givrish.Dashboard;
import com.example.givrish.R;
import com.example.givrish.interfaces.ItemSelectedListener;
import com.example.givrish.interfaces.ListCallBackEvent;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.models.LocationClass;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.viewmodel.ListViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.givrish.database.Constants.CURRENT_USER_PROFILE_PICTURE;

public class ListFragment extends Fragment implements ListCallBackEvent {

  public static final String CATEGORIES_FRAGMENT_FLAG= "4";
  public static final String SEARCH_FRAGMENT_FLAG = "6";

  private ListViewModel mViewModel;
  private CircleImageView profile;
  private Fragment fragment;
  private ApiEndpointInterface apiService;
  private ProgressBar loading;
  private ListItemAdapter listItemAdapter;
  private ListCallBackEvent listCallBackEvent;
  private RecyclerView listRecyclerView;
  private LocationClass locationClass;
  private LocationClass.LocationResult locationResult;
  boolean check = false;
  private String[] locationData;
  String pic;

  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    setHasOptionsMenu(true);
    listCallBackEvent = this;
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
    getAllItems();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_list_item, container, false);

    Toolbar toolbar = view.findViewById(R.id.list_item_toolbar);
    profile = view.findViewById(R.id.circleImageView_profile);
    listRecyclerView = view.findViewById(R.id.listItem);
    loading = view.findViewById(R.id.loading_items);
    final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.items_swipe_refresh);

    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
    swipeRefreshLayout.post(new Runnable() {
      @Override
      public void run() {
        getAllItems();
      }
    });

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        getAllItems();
        swipeRefreshLayout.setRefreshing(false);
      }
    });

    if (getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    mViewModel.getItems().observe(this, new Observer<List<AllItemsResponseData>>() {
      @Override
      public void onChanged(List<AllItemsResponseData> allItemsResponseData) {
        listItemAdapter.setAllItemsResponseData(allItemsResponseData);
      }
    });

    if(getArguments() != null){
      pic=getArguments().getString("pic");
      Drawable theImage = Drawable.createFromPath(pic);
      profile.setImageDrawable(theImage);
    }
    else {
      loadProfilePicture();
    }

    inflateRecycler();

    toolbar.setTitle("Givrish");
    return view;
  }

  public void loadProfilePicture() {
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
    String picUrl = "http://givrishapi.divinepagetech.com/profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/";
    try {
      String uri =  picUrl + CURRENT_USER_PROFILE_PICTURE;
      Picasso.get().load(uri).resize(100, 100).noFade().into(profile);
    }
    catch (Exception e){
      e.printStackTrace();
      profile.setImageResource(R.drawable.defaultprofile);
    }
  }

  private void inflateRecycler() {
    listItemAdapter = new ListItemAdapter(getContext());
    listRecyclerView.setAdapter(listItemAdapter);
    listRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2) );
  }

  private void getAllItems() {
    ApiKey apiKey = new ApiKey("test");
    Gson gson = new Gson();

    String stringApiKey = gson.toJson(apiKey);
    Call<AllItemsResponse> call = apiService.getAllItems(stringApiKey);
    call.enqueue(new Callback<AllItemsResponse>() {
      @Override
      public void onResponse(@NonNull Call<AllItemsResponse> call,@NonNull Response<AllItemsResponse> response) {
        if (response.isSuccessful()) {
          if (response.body() != null && response.body().getResponseCode().equals("1")) {
            listCallBackEvent.itemsLoaded(response.body().getData());
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<AllItemsResponse> call, @NonNull Throwable t) {
        if (getView() != null)
        Snackbar.make(getView(), "Please check your network", Snackbar.LENGTH_SHORT)
                .show();
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    locationClass=new LocationClass();
    displayLocation();

    profile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragment = new ProfileFragment();
        if(pic!=null){
          Bundle bundle=new Bundle();
          bundle.putString("pic", pic);
          fragment.setArguments(bundle);
        }
        loadFragment(fragment, Dashboard.PROFILE_PAGE_FLAG);
      }
    });

  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main_toolbar_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_hamburger){
      fragment = new CategoryFragment();
      loadFragment(fragment, CATEGORIES_FRAGMENT_FLAG);
    } else if (item.getItemId() == R.id.search_menu) {
      fragment = new SearchFragment();
      loadFragment(fragment, SEARCH_FRAGMENT_FLAG);
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadFragment(Fragment fragment, String tag) {
    FragmentTransaction transaction;
    if(getActivity() != null) {
      transaction = getActivity().getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.dashboard_layout, fragment, tag);
      transaction.addToBackStack(tag);
      transaction.commit();
    }
  }

  @Override
  public void itemsLoaded(List<AllItemsResponseData> items) {
    mViewModel.insertAllItems(getNewItems(items));
    listItemAdapter.setAllItemsResponseData(items);
    loading.setVisibility(View.INVISIBLE);

  }

  // This get new items into database by selecting last 20;
  private List<AllItemsResponseData> getNewItems(List<AllItemsResponseData> items) {
    if (items.size() > 20){
      int lastIndex = items.size();
      int fromIndex = lastIndex - 20;
      return items.subList(fromIndex, lastIndex);
    }
    return items;
  }

  private void displayLocation() {

    locationResult = new LocationClass.LocationResult(){

      @Override
      public void gotLocation(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
          List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

          String country = addressList.get(0).getCountryName();
          String state = addressList.get(0).getAdminArea();
          String addressLine = addressList.get(0).getAddressLine(0);

          DecimalFormat df = new DecimalFormat("#.###");

          String lng = df.format(addressList.get(0).getLongitude());
          String lat = df.format(addressList.get(0).getLatitude());

          listItemAdapter.setLongitude(lng);
          listItemAdapter.setLatitude(lat);

          //try to use if statement for checking empty string
          String addr = country + "\\" + state + "\\" + addressLine + "\\" + lng + "\\" + lat;
          locationData = addr.split("\\\\");

        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    };

    check = locationClass.getLocation(getContext(), locationResult);

    if(!check)
      //Ask for permission
      if (getActivity() != null)
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

  }

}
