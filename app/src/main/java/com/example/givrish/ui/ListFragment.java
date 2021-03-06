package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
import com.example.givrish.UserDataPreference;
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
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.givrish.database.Constants.*;
import static com.example.givrish.database.Constants.CURRENT_USER_EMAIL;
import static com.example.givrish.database.Constants.CURRENT_USER_FULLNAME;
import static com.example.givrish.database.Constants.CURRENT_USER_ID;
import static com.example.givrish.database.Constants.CURRENT_USER_PHONE_NUMBER;
import static com.example.givrish.database.Constants.CURRENT_USER_PROFILE_PICTURE;

public class ListFragment extends Fragment implements ListCallBackEvent {

  public static final String CATEGORIES_FRAGMENT_FLAG= "4";
  private static final String SEARCH_FRAGMENT_FLAG = "6";

  private ListViewModel mViewModel;
  private CircleImageView profile;
  private Fragment fragment;
  private ApiEndpointInterface apiService;
  private ProgressBar loading;
  private ListItemAdapter listItemAdapter;
  private ListCallBackEvent listCallBackEvent;
  private RecyclerView listRecyclerView;
  private LocationClass locationClass;
  private Executor executor;
  private String filterValue = "";


  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setConstants();
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    setHasOptionsMenu(true);
    listCallBackEvent = this;
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
    getAllItems("");
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

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        getAllItems(filterValue);
        swipeRefreshLayout.setRefreshing(false);
      }
    });

    if (getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

//      mViewModel.getItems().observe(this, new Observer<List<AllItemsResponseData>>() {
//          @Override
//          public void onChanged(List<AllItemsResponseData> allItemsResponseData) {
//              listItemAdapter.setAllItemsResponseData(allItemsResponseData);
//          }
//      });

      profile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              fragment = new ProfileFragment();
              if (CURRENT_USER_PROFILE_PICTURE != null) {
                  Bundle bundle = new Bundle();
                  bundle.putString("pic", CURRENT_USER_PROFILE_PICTURE);
                  fragment.setArguments(bundle);
              }
              loadFragment(fragment, Dashboard.PROFILE_PAGE_FLAG);
          }
      });

      try {
          if (CURRENT_USER_PROFILE_PICTURE.isEmpty() || !PROFILE_PICTURE) {
              executor.execute(new Runnable() {
                  @Override
                  public void run() {
                      loadProfilePicture();
                  }
              });
          } else{
              if(PROFILE_PICTURE) {
                Drawable drawable = Drawable.createFromPath(CURRENT_USER_PROFILE_PICTURE);
                  profile.setImageDrawable(drawable);
              }
              else{
                  profile.setImageResource(R.drawable.defaultprofile);
              }
          }
      }catch (Exception e){
          profile.setImageResource(R.drawable.defaultprofile);
      }

      inflateRecycler();

      toolbar.setTitle("Givrish");
      return view;
  }


  private void loadProfilePicture() {
        apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        String picUrl = "http://givrishapi.divinepagetech.com/profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/";
        String uri =  picUrl + CURRENT_USER_PROFILE_PICTURE;

        try {
            Picasso.get().load(uri).resize(100, 100).noFade().into(profile);
            UserDataPreference.getInstance(getContext()).savePreference(getString(R.string.PicAvailable), "true");

        } catch (Exception e) {
            profile.setImageResource(R.drawable.defaultprofile);
            e.printStackTrace();
        }
  }

  private void inflateRecycler() {
    listItemAdapter = new ListItemAdapter(getContext());
    listRecyclerView.setAdapter(listItemAdapter);
    listRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
  }

  @Override
  public void onResume() {
    super.onResume();
    setConstants();
  }

  private void setConstants() {
    CURRENT_USER_ID = UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.user_id));
    CURRENT_USER_FULLNAME = UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.user_fullname_Keystore));
    CURRENT_USER_EMAIL = UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.user_email_Keystore));
    CURRENT_USER_PHONE_NUMBER = UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.user_phone_number_Keystore));
    CURRENT_USER_PROFILE_PICTURE = UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.user_picture));
    PROFILE_PICTURE = Boolean.valueOf(UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.PicAvailable)));

  }
  private void getAllItems(String subCategory) {
    ApiKey apiKey;
    if (!subCategory.isEmpty()) {
      apiKey = new ApiKey("test", "", subCategory);
    } else {
      apiKey = new ApiKey("test");
    }

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
        Snackbar.make(getView(), "Please check your network", Snackbar.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    locationClass=new LocationClass();
    displayLocation();
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
    listRecyclerView.setVisibility(View.VISIBLE);
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
    LocationClass.LocationResult locationResult = new LocationClass.LocationResult() {

      @Override
      public void gotLocation(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
          List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
          DecimalFormat df = new DecimalFormat("#.###");

          String lng = df.format(addressList.get(0).getLongitude());
          String lat = df.format(addressList.get(0).getLatitude());

          listItemAdapter.setLongitude(lng);
          listItemAdapter.setLatitude(lat);

        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    };

    boolean check = locationClass.getLocation(getContext(), locationResult);

    if(!check)
      //Ask for permission
      if (getActivity() != null)
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

  }

  public void filter(String subCategoryId) {
    this.filterValue = subCategoryId;
    loading.setVisibility(View.VISIBLE);
    listRecyclerView.setVisibility(View.INVISIBLE);
    getAllItems(subCategoryId);
  }
}
