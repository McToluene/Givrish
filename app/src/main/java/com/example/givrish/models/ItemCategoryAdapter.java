package com.example.givrish.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.database.CategoryCallbackEvent;
import com.example.givrish.ui.ItemSubCategoryFragment;

import java.util.List;
import java.util.Random;

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
        holder.Position = position;

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
        private int Position;


        public ItemCategoryHolder(@NonNull View itemView) {
            super(itemView);
            fTitle =  itemView.findViewById(R.id.sub_tvv);
            itemCatTitle = itemView.findViewById(R.id.sub_tv_name_v);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ITEM_POSITION",Position);
                    bundle.putString("ITEM",itemCategoryData.get(Position).getItem_category_id());
                    ItemSubCategoryFragment itemSub = new ItemSubCategoryFragment();
                    itemSub.setArguments(bundle);
                    loadSub(itemSub);
//                    mEvent.BackgroundResult("3",itemCategoryData.get(Position));
                }

                private void loadSub(Fragment itemSub) {
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.dashboard_layout, itemSub);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }

    }
}
