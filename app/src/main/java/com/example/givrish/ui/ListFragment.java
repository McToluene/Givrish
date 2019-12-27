package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.givrish.R;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.models.ProductModel;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.viewmodel.ListViewModel;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

  public static final String CATEGORIES_FRAGMENT_FLAG= "4";
  public static final String PROFILE_FRAGMENT_FLAG = "5";
  public static final String SEARCH_FRAGMENT_FLAG = "6";


  private ListViewModel mViewModel;
  private RecyclerView listRecyclerView;
  private CircleImageView profile;
  private Fragment fragment;
  private ApiEndpointInterface apiService;
  private ProgressBar loading;
  private List<AllItemsResponseData> itemSModel;
  private ListItemAdapter listItemAdapter;

  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
    getAllItems();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_list_item, container, false);
    Toolbar toolbar = view.findViewById(R.id.list_item_toolbar);
    profile = view.findViewById(R.id.circleImageView_profile);
    listRecyclerView = view.findViewById(R.id.listItem);
    loading = view.findViewById(R.id.items_loading);
    loading.setVisibility(View.VISIBLE);

    if (getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
    
    toolbar.setTitle("Givrish");

    return view;
  }

  private void getAllItems() {
    ApiKey apiKey = new ApiKey("test");
    Gson gson = new Gson();

    String stringApiKey = gson.toJson(apiKey);

    Call<AllItemsResponse> call = apiService.getAllItems(stringApiKey);
    call.enqueue(new Callback<AllItemsResponse>() {
      @Override
      public void onResponse(@NonNull Call<AllItemsResponse> call,@NonNull Response<AllItemsResponse> response) {
        if (response.body() != null && response.body().getResponseCode().equals("1")) {
          Log.i("MESSAGE", response.toString() );
          listItemAdapter.setAllItemsResponseData(response.body().getData());
          itemSModel = response.body().getData();
          Log.i("INNER COUNT", String.valueOf(itemSModel.size()));
          loading.setVisibility(View.INVISIBLE);
          listRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
          listRecyclerView.setAdapter(listItemAdapter);
        }
      }

      @Override
      public void onFailure(@NonNull Call<AllItemsResponse> call, @NonNull Throwable t) {
        Toast.makeText(getContext(), "Please Check your message", Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    // TODO: Use the ViewModel

    listItemAdapter = new ListItemAdapter(getContext());

    Log.i("COUNT", String.valueOf( listItemAdapter.getItemCount()) );
//    listItemAdapter.getItemCount();

    profile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        fragment = new ProfileFragment();
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
}
