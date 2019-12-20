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

import java.util.List;
import java.util.Random;

public class ItemSubCategoryAdapter extends RecyclerView.Adapter<ItemSubCategoryAdapter.ItemSubCategoryHolder> {
    private Context mContext;
    private List<ItemSubCategoryModel> itemSubCategoryModels;
    private LayoutInflater inflater;
    private CategoryCallbackEvent mEvent;

    public ItemSubCategoryAdapter(Context mContext, List<ItemSubCategoryModel> itemSubCategoryModels) {
        this.mContext = mContext;
        this.itemSubCategoryModels = itemSubCategoryModels;
        inflater = LayoutInflater.from(mContext);
    }

    public ItemSubCategoryAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ItemSubCategoryAdapter.ItemSubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSubCategoryview = inflater.inflate(R.layout.item_category,parent,false);
        return new ItemSubCategoryHolder(itemSubCategoryview);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSubCategoryAdapter.ItemSubCategoryHolder holder, int position) {
        holder.itemSubCatTitle.setText(itemSubCategoryModels.get(position).getItem_sub_category_name());
        holder.fSubTitle.setText(itemSubCategoryModels.get(position).getItem_sub_category_name().subSequence(0,1));
       holder.Position = position;

        GradientDrawable drawable = (GradientDrawable) holder.fSubTitle.getBackground();
        Random randomColor = new Random();
        int color = Color.argb(100,randomColor.nextInt(110),randomColor.nextInt(110),randomColor.nextInt(110));
        drawable.setColor(color);

    }

    public void setCategories(List<ItemSubCategoryModel> vSubModel){
        this.itemSubCategoryModels = vSubModel;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return itemSubCategoryModels == null ? 0 : itemSubCategoryModels.size();
    }

    public class ItemSubCategoryHolder extends RecyclerView.ViewHolder {
        private TextView fSubTitle;
        private AppCompatTextView itemSubCatTitle;
        private int Position;

        public ItemSubCategoryHolder(@NonNull View itemView) {
            super(itemView);
            fSubTitle = itemView.findViewById(R.id.sub_tv);
            itemSubCatTitle = itemView.findViewById(R.id.sub_tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });



        }
    }
}
