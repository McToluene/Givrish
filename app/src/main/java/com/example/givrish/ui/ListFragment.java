package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.givrish.R;
import com.example.givrish.interfaces.ListCallBackEvent;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.viewmodel.ListViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.givrish.database.Constants.CURRENT_USER_PROFILE_PICTURE;

public class ListFragment extends Fragment implements ListCallBackEvent {

  public static final String CATEGORIES_FRAGMENT_FLAG= "4";
  public static final String PROFILE_FRAGMENT_FLAG = "5";
  public static final String SEARCH_FRAGMENT_FLAG = "6";


  private ListViewModel mViewModel;
  private CircleImageView profile;
  private Fragment fragment;
  private ApiEndpointInterface apiService;
  private ProgressBar loading;
  private ListItemAdapter listItemAdapter;
  private ListCallBackEvent listCallBackEvent;
  private RecyclerView listRecyclerView;
  private List<AllItemsResponseData> items;

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

  Bitmap theImage;
  @Override
  public void onResume() {
    super.onResume();
    if(ProfileEditFragment.returnValue.isEmpty() || ProfileEditFragment.returnValue.get(0).isEmpty())
      loadProfilePicture();
    else
      theImage = BitmapFactory.decodeFile(ProfileEditFragment.returnValue.get(0));
    profile.setImageBitmap(theImage);
  }

  private void loadProfilePicture() {
    String picUrl = "http://givrishapi.divinepagetech.com/profilepix787539489ijkjfidj84u3i4kjrnfkdyeu4rijknfduui4jrkfd8948uijrkfjdfkjdk/";

    try {
      String uri =  picUrl + CURRENT_USER_PROFILE_PICTURE;
      Picasso.with(getContext()).load(uri).resize(80, 80).noFade().into(profile);
    }
    catch (Exception e){
      e.printStackTrace();
    }
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

    LiveData<List<AllItemsResponseData>> itemsList = mViewModel.getItems();
    items = itemsList.getValue();
    mViewModel.getItems().observe(this, new Observer<List<AllItemsResponseData>>() {
      @Override
      public void onChanged(List<AllItemsResponseData> allItemsResponseData) {
        items = allItemsResponseData;

      }
    });

    inflateRecycler();

    toolbar.setTitle("Givrish");
    return view;
  }

  private void inflateRecycler() {
    listItemAdapter = new ListItemAdapter(getContext(), items);
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
        Snackbar.make(getView(), "Please check your network", Snackbar.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    profile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragment = new ProfileFragment();
        loadFragment(fragment, PROFILE_FRAGMENT_FLAG);
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
}
