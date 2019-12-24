package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.givrish.R;
import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.models.ItemCategoryAdapter;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ApiKey;
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
    private static final String api_key = "test";
    private CallBackListener callBackListener;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBackListener = (CallBackListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, container, false);
        toolbar = view.findViewById(R.id.cate_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setTitle("Categories");
        return view;
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
        if (getActivity() != null) {
            recyclerView = getActivity().findViewById(R.id.listItemCategory);
            recyclerView.setHasFixedSize(true);
        }

        itemCategoryAdapter = new ItemCategoryAdapter(getContext());
        recyclerView.setAdapter(itemCategoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        loadCategoryItems(api_key);
    }

    private void loadCategoryItems(String api_key) {
        final ApiKey apiKey = new ApiKey(api_key);
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        Gson gson = new Gson();
        String itemString = gson.toJson(apiKey);
        Call<ItemCategoryResponse> callItem = apiService.getCategory(itemString);
        callItem.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemCategoryResponse> call, @NonNull Response<ItemCategoryResponse> response) {
                if( response.body() != null && response.body().getResponseCode().equals("1")){
                    mViewModel.insert(new ArrayList<>(response.body().getData()) );
                } else if (response.body() != null) {
                    Toast.makeText(getActivity(),response.body().getResponseStatus(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemCategoryResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            callBackListener.onBackClick(ListFragment.CATEGORIES_FRAGMENT_FLAG);
        }

        return super.onOptionsItemSelected(item);
    }
}
