package com.example.givrish.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.database.CategoryCallbackEvent;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.ui.CategoryFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ItemCategoryHolder> {
    private List<ItemCategoryData> itemCategoryData;
    private Context context;
    private LayoutInflater inflater;
    private CategoryCallbackEvent mEvent;

    public ItemCategoryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemCategoryAdapter.ItemCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_category,parent,false);
        return new ItemCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCategoryAdapter.ItemCategoryHolder holder, int position) {
        holder.itemCatTitle.setText(itemCategoryData.get(position).getItem_category_name());
        holder.fTitle.setText(itemCategoryData.get(position).getItem_category_name().subSequence(0,1));
        holder.position = position;

        GradientDrawable drawable = (GradientDrawable) holder.fTitle.getBackground();
        Random randomColor = new Random();
        int color = Color.argb(100,randomColor.nextInt(110),randomColor.nextInt(110),randomColor.nextInt(110));
        drawable.setColor(color);


    }

    public void setCategories(List<ItemCategoryData> itemCategoryData){
        this.itemCategoryData = itemCategoryData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemCategoryData == null ? 0 : itemCategoryData.size();
    }

    public class ItemCategoryHolder extends RecyclerView.ViewHolder {

        private TextView fTitle;
        private AppCompatTextView itemCatTitle;
        private int position;

        public ItemCategoryHolder(@NonNull View itemView) {
            super(itemView);
            fTitle =  itemView.findViewById(R.id.sub_tv);
            itemCatTitle = itemView.findViewById(R.id.sub_tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    onCheckSubCategory(view, CategoryFragment.api_key);

                }
            });

        }

//        private void onCheckSubCategory(final View view, String api_key) {
//             ItemSubCategoryModel itemSubCategoryModel = new ItemSubCategoryModel(api_key);
//            ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
//            Gson gson = new Gson();
//            String itemString = gson.toJson(itemSubCategoryModel);
//            Call<ItemCategoryResponse> callSubItem = apiService.getSubCategory(itemString);
//            callSubItem.enqueue(new Callback<ItemCategoryResponse>() {
//                @Override
//                public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
//                    if(response.body().getResponseCode().equals("1")){
//                        Snackbar.make(view,response.body().getRecordCount(),Snackbar.LENGTH_LONG).show();
//                    }else if (response.body().getResponseCode().equals("0")){
//                        Snackbar.make(view,response.body().getResponseStatus(),Snackbar.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
//
//                }
//            });
//
//        }
    }
}
