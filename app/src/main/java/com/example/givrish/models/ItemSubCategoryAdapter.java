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

import com.example.givrish.ItemSubCategoryData;
import com.example.givrish.R;
import com.example.givrish.database.CategoryCallbackEvent;

import java.util.List;
import java.util.Random;

public class ItemSubCategoryAdapter extends RecyclerView.Adapter<ItemSubCategoryAdapter.ItemSubCategoryHolder> {
    private Context mContext;
    private List<ItemSubCategoryData> itemSubCategoryData;
    private LayoutInflater inflater;
    private CategoryCallbackEvent mEvent;

    public ItemSubCategoryAdapter(Context mContext, List<ItemSubCategoryData> itemSubCategoryData) {
        this.mContext = mContext;
        this.itemSubCategoryData = itemSubCategoryData;
        inflater = LayoutInflater.from(mContext);
    }

    public ItemSubCategoryAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public ItemSubCategoryAdapter(List<ItemSubCategoryData> itemSubCategoryDataList, Context context) {
        this.itemSubCategoryData = itemSubCategoryDataList;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ItemSubCategoryAdapter.ItemSubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSubCategoryview = inflater.inflate(R.layout.item_sub_category,parent,false);
        return new ItemSubCategoryHolder(itemSubCategoryview);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSubCategoryAdapter.ItemSubCategoryHolder holder, int position) {
        holder.itemSubCatTitle.setText(itemSubCategoryData.get(position).getItem_sub_category_name());
        holder.fSubTitle.setText(itemSubCategoryData.get(position).getItem_sub_category_name().subSequence(0,1));
       holder.Position = position;

        GradientDrawable drawable = (GradientDrawable) holder.fSubTitle.getBackground();
        Random randomColor = new Random();
        int color = Color.argb(100,randomColor.nextInt(110),randomColor.nextInt(110),randomColor.nextInt(110));
        drawable.setColor(color);

    }

    public void setCategories(List<ItemSubCategoryData> vSubModel){
        this.itemSubCategoryData = vSubModel;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return itemSubCategoryData == null ? 0 : itemSubCategoryData.size();
    }

    public class ItemSubCategoryHolder extends RecyclerView.ViewHolder {
        private TextView fSubTitle;
        private AppCompatTextView itemSubCatTitle;
        private int Position;

        public ItemSubCategoryHolder(@NonNull View itemView) {
            super(itemView);
            fSubTitle = itemView.findViewById(R.id.sub_tvv);
            itemSubCatTitle = itemView.findViewById(R.id.sub_tv_name_v);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });



        }
    }
}
