package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
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
import com.example.givrish.viewmodel.CategoryViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private Toolbar toolbar;
    private CategoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private ItemCategoryAdapter itemCategoryAdapter;
    public static final String api_key = "test";

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.cate_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
//        toolbar.setNavigationIcon(R.drawable.ic_back_icon);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setTitle("Categories");

        return inflater.inflate(R.layout.category_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.getAllCategories().observe(this, new Observer<List<ItemCategoryData>>() {
            @Override
            public void onChanged(List<ItemCategoryData> itemCategoryData) {
                itemCategoryAdapter.setCategories(itemCategoryData);
            }
        });
        initiateView();

    }

    private void initiateView() {
        recyclerView = getActivity().findViewById(R.id.listItemCategory);
        recyclerView.setHasFixedSize(true);
        itemCategoryAdapter = new ItemCategoryAdapter(getContext());
        recyclerView.setAdapter(itemCategoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

                if(response.body().getResponseCode().equals("1")){
                    mViewModel.insert(new ArrayList<>(response.body().getData()) );
                }else
             Toast.makeText(getActivity(),response.body().getResponseStatus(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
