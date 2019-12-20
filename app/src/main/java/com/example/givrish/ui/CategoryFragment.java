package com.example.givrish.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.givrish.R;
import com.example.givrish.models.ItemCategoryAdapter;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemCategoryModel;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private CategoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<ItemCategoryData> items;
    private ItemCategoryAdapter itemCategoryAdapter;
    public static final String api_key = "test";

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel
        initiateView();


//        List<ProductModel> productModelsCategory = ProductModel.createProductCategory();
//        Log.i("Category",productModelsCategory.toString());
//           LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        RecyclerView categoryRecycler = getActivity().findViewById(R.id.listItemCategory);
//        categoryRecycler.setLayoutManager(layoutManager);

//        categoryRecycler.setAdapter(new ItemCategoryAdapter(productModelsCategory,getContext()));

    }

    private void initiateView() {
        recyclerView = getActivity().findViewById(R.id.listItemCategory);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        loadCategoryItems(api_key);
    }

    private void loadCategoryItems(String api_key) {
        final ItemCategoryModel itemCategoryModel = new ItemCategoryModel(api_key);
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        Gson gson = new Gson();
        String itemString = gson.toJson(itemCategoryModel);
        Call<ItemCategoryResponse> callItem = apiService.getCategory(itemString);
        callItem.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                             Toast.makeText(getActivity(),response.body().getResponseStatus(),Toast.LENGTH_LONG).show();
                if(response.body().getResponseCode().equals("1")){
                    items = new ArrayList<>(response.body().getData());
                    itemCategoryAdapter = new ItemCategoryAdapter(items,getContext());
                    recyclerView.setAdapter(itemCategoryAdapter);
                }else
             Toast.makeText(getActivity(),response.body().getResponseStatus(),Toast.LENGTH_LONG).show();
//             dataItem = new ArrayList<>(Arrays.asList(response.body().getItem()));
//             itemCategoryAdapter = new ItemCategoryAdapter(dataItem);
////             recyclerView.setAdapter(itemCategoryAdapter);



//
//
//
//                items = new ArrayList<>(Arrays.asList((ItemCategoryData) response.body().getData()));
//            itemCategoryAdapter = new ItemCategoryAdapter(items);
//            recyclerView.setAdapter(itemCategoryAdapter);
            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

//        Call<List<ItemCategoryData>> callItems = apiService.getCategory(itemString);





    }

}
