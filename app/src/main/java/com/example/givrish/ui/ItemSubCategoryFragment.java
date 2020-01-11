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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ItemCategoryAdapter;
import com.example.givrish.models.ItemSubCategoryAdapter;
import com.example.givrish.models.ItemSubCategoryData;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.viewmodel.ItemSubCategoryViewModel;
import com.example.givrish.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemSubCategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar toolbar;
    private ItemSubCategoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private ItemSubCategoryAdapter itemSubCategoryAdapter;
    private  static final String apiKeySub= "com.example.givrish.ui.APIKEYY";
    private CallBackListener callBackListener;
    SwipeRefreshLayout subSwipeToRefresh;
    private ProgressBar subCatProgress;
    public static final String SUBCATEGORYFLAG ="1997";
    public static String ademi;


    public static ItemSubCategoryFragment newInstance() {
        return new ItemSubCategoryFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callBackListener = (CallBackListener) context;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_sub_category_fragment, container, false);

        toolbar = view.findViewById(R.id.cate_toolbarr);
        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getContext()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
        ((AppCompatActivity)getContext()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("SubCategories");
        subSwipeToRefresh = view.findViewById(R.id.sub_swipeRefresh);

        subCatProgress = view.findViewById(R.id.sub_category_progressbar);
        subSwipeToRefresh.setOnRefreshListener(this);
        subSwipeToRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

        recyclerView = view.findViewById(R.id.listItemSubCategory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemSubCategoryAdapter = new ItemSubCategoryAdapter(getContext());
        recyclerView.setAdapter(itemSubCategoryAdapter);
        subCatApiList(ademi);
        try {
            //Todo...
            ademi = getArguments().getString("ITEMM");
        }catch (Exception e){ e.printStackTrace();Toast.makeText(getContext(),"Request failed",Toast.LENGTH_LONG).show(); }
        //Todo B4 Update

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ItemSubCategoryViewModel.class);

        try {
            mViewModel.getSubsub().observe(this, new Observer<List<ItemSubCategoryData>>() {
                @Override
                public void onChanged(List<ItemSubCategoryData> itemSubCategoryData) {
                    subSwipeToRefresh.setRefreshing(false);
                    subCatProgress.setVisibility(View.INVISIBLE);
                    itemSubCategoryAdapter.setCategories(itemSubCategoryData);
                }
            });
        }catch (Exception e){}
    }


    private void subCatApiList(final String a){
        ApiKey itemCategoryModel = new ApiKey("test");
        ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
        Gson gson = new Gson();
        String itemString = gson.toJson(itemCategoryModel);
        Call<ItemSubCategoryResponse> callSub = apiService.getSubCategory(itemString);
        callSub.enqueue(new Callback<ItemSubCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemSubCategoryResponse> call, Response<ItemSubCategoryResponse> response) {
                if (response.body() != null && response.body().getResponseCode().equals("1")) {
                        subCatProgress.setVisibility(View.VISIBLE);
                        mViewModel.insertAllSub(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<ItemSubCategoryResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_erroe), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            callBackListener.onBackClick(ItemCategoryAdapter.SUB_CATEGORIES_FRAGMENT_fLAG);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRefresh() {
         subSwipeToRefresh.setRefreshing(true);
    }
}
