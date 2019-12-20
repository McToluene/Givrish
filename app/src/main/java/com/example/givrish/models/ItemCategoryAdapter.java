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

import java.util.ArrayList;
import java.util.List;


public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ItemCategoryHolder> {
    private List<ItemCategoryData> itemCategoryData;
    private Context context;
    private LayoutInflater inflater;

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
            fTitle =  itemView.findViewById(R.id.cd_image_text);
            itemCatTitle = itemView.findViewById(R.id.tv_cat_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }
    }
}
