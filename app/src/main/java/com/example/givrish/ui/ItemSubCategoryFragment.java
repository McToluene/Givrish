package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.givrish.ItemSubCategoryData;
import com.example.givrish.models.ItemCategoryModel;
import com.example.givrish.models.ItemSubCategoryAdapter;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.viewmodel.ItemSubCategoryViewModel;
import com.example.givrish.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemSubCategoryFragment extends Fragment {
    private Toolbar toolbar;
    private ItemSubCategoryViewModel mViewModel;
    private int itemcatPositionId;
    private List<ItemSubCategoryData> itemSubCategoryDataList;
    private RecyclerView recyclerView;
    private ItemSubCategoryAdapter itemSubCategoryAdapter;
    private  static final String apiKeySub= "com.example.givrish.ui.APIKEYY";

    public static ItemSubCategoryFragment newInstance() {
        return new ItemSubCategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.cate_toolbarr);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return inflater.inflate(R.layout.item_sub_category_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ItemSubCategoryViewModel.class);
        // TODO: Use the ViewModel

        try {
        itemcatPositionId = getArguments().getInt("ITEM_POSITION");
    }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Request failed",Toast.LENGTH_LONG).show();
        }
        intiateView();
    }


    private void intiateView() {
        recyclerView = getActivity().findViewById(R.id.listItemSubCategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       apiSubCategoryList(String.valueOf(itemcatPositionId));
    }

    private void apiSubCategoryList(final String selectedCategory) {
        ItemCategoryModel itemCategoryModel = new ItemCategoryModel(apiKeySub);
        final List<ItemSubCategoryData> subCategoryData = new ArrayList<>();
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        Gson gson = new Gson();
        String itemsubString = gson.toJson(itemCategoryModel);
        Call<ItemSubCategoryResponse> callSubItem = apiService.getSubCategory(itemsubString);
        callSubItem.enqueue(new Callback<ItemSubCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemSubCategoryResponse> call, Response<ItemSubCategoryResponse> response) {
                if (response.body().getResponseCode().equals("1")) {
                    itemSubCategoryDataList = response.body().getData();
                    String morning;
                    for (int i = 0; i < itemSubCategoryDataList.size(); i++) {
                        morning = itemSubCategoryDataList.get(i).getItem_category_id();
                        if (i == itemSubCategoryDataList.size()) { break;
                        }else if (morning.equals(selectedCategory)) {
                          subCategoryData.add(itemSubCategoryDataList.get(i));
                          Collections.sort(subCategoryData,ItemSubCategoryData.SubCategoryComparator);
                            itemSubCategoryAdapter = new ItemSubCategoryAdapter(subCategoryData,getContext());
                          recyclerView.setAdapter(itemSubCategoryAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemSubCategoryResponse> call, Throwable t) {
                  Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
