package com.example.givrish.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.givrish.R;
import com.example.givrish.interfaces.SimilarItemsCallBack;
import com.example.givrish.models.AllItemsResponse;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimilarFragment extends Fragment implements SimilarItemsCallBack {
  private static final String CATEGORY_ID_KEY = "com.example.givrish.ui.CATEGORY_KEY";
  private static final String SUBCATEGORY_ID_KEY = "com.example.givrish.ui.SUBCATEGORY_KEY";
  private SimilarItemsCallBack event;
  private ApiEndpointInterface apiService;
  private ListItemAdapter adapter;
  private String categoryId;
  private String subCategory;
  private Executor executor = Executors.newSingleThreadExecutor();
  private ProgressBar similarLoading;

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
    event = this;
    getSimilarItems();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_similar, container, false);

    if (getArguments() != null) {
      categoryId = getArguments().getString(CATEGORY_ID_KEY);
      subCategory = getArguments().getString(SUBCATEGORY_ID_KEY);
    }

    similarLoading = view.findViewById(R.id.similar_loading);

    RecyclerView similarRecycler = view.findViewById(R.id.similar_recycler);
    adapter = new ListItemAdapter(getContext());
    similarRecycler.setAdapter(adapter);
    similarRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

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
            event.filterItems(response.body().getData());
            similarLoading.setVisibility(View.INVISIBLE);
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
  public void filterItems(final List<AllItemsResponseData> items) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        List <AllItemsResponseData> similarItems = new ArrayList<>();
        for (AllItemsResponseData item : items) {
          if ((item.getItem_category_id().equals(categoryId) || item.getItem_sub_category_id().equals(subCategory))) {
            similarItems.add(item);
          }
        }
        event.removeTop(similarItems);
      }
    });
  }

  // Get the top 20 items
  @Override
  public void removeTop(final List<AllItemsResponseData> similarItems) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (similarItems.size() > 10){
          int lastIndex = similarItems.size();
          int fromIndex = lastIndex - 10;
          event.loadItems(similarItems.subList(fromIndex, lastIndex));
        }
      }
    });
  }

  //Loading items into the adapter using the main ui thread
  @Override
  public void loadItems(final List<AllItemsResponseData> subList) {
    if (getActivity() != null) {
      getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          adapter.setAllItemsResponseData(subList);
        }
      });
    }
  }


}
