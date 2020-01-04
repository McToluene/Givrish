package com.example.givrish.ui;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.givrish.R;
import com.example.givrish.interfaces.ListCallBackEvent;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimilarFragment extends Fragment implements ListCallBackEvent {
  private static final String CATEGORY_ID_KEY = "com.example.givrish.ui.CATEGORY_KEY";
  private static final String SUBCATEGORY_ID_KEY = "com.example.givrish.ui.SUBCATEGORY_KEY";
  private ListCallBackEvent listCallBackEvent;
  private ApiEndpointInterface apiService;
  private ListItemAdapter adapter;

  public SimilarFragment() {
    // Required empty public constructor
  }

  public static SimilarFragment newInstance(String categoryId, String subCategoryId) {

    Bundle args = new Bundle();
    args.putString(CATEGORY_ID_KEY, categoryId);
    args.putString(SUBCATEGORY_ID_KEY, subCategoryId);
    SimilarFragment fragment = new SimilarFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
    listCallBackEvent = this;
    getSimilarItems();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_similar, container, false);
    RecyclerView similarRecycler = view.findViewById(R.id.similar_recycler);
    adapter = new ListItemAdapter(getContext());
    similarRecycler.setAdapter(adapter);
    similarRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

    return view;
  }

  private void getSimilarItems() {
    ApiKey apiKey = new ApiKey("test");
    Gson gson = new Gson();

    String stringApiKey = gson.toJson(apiKey);
    Call<AllItemsResponse> call = apiService.getAllItems(stringApiKey);
    call.enqueue(new Callback<AllItemsResponse>() {
      @Override
      public void onResponse(@NonNull Call<AllItemsResponse> call, @NonNull Response<AllItemsResponse> response) {
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
  public void itemsLoaded(List<AllItemsResponseData> items) {
    adapter.setAllItemsResponseData(items);
  }
}
