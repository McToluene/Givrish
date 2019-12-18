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
import java.util.Random;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ItemCategoryHolder> {
    private List<ItemCategoryData> itemCategoryData;
    private ItemCategoryData itemCategoryDataObject;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ItemCategoryData> Aitem;

    public ItemCategoryAdapter(List<ItemCategoryData> itemCategoryData, Context context) {
        this.itemCategoryData = itemCategoryData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    public ItemCategoryAdapter(ArrayList<ItemCategoryData> items) {
         this.Aitem = items;
    }

    public ItemCategoryAdapter(List<ItemCategoryData> items) {
      this.itemCategoryData = items;
    }


    @NonNull
    @Override
    public ItemCategoryAdapter.ItemCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.item_category,parent,false);
        return new ItemCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCategoryAdapter.ItemCategoryHolder holder, int position) {
        itemCategoryDataObject = itemCategoryData.get(position);
        holder.itemCatTitle.setText(itemCategoryDataObject.getItem_category_name());
        holder.fTitle.setText(itemCategoryDataObject.getItem_category_name().subSequence(0,1));

//        holder.fTitle.setText(Aitem.get(position).getItem_category_name());
//        holder.itemCatTitle.setText(Aitem.get(position).getItem_category_name().subSequence(0,1));

//     holder.fTitle.setText(itemCategoryData.get(position).getItem_category_name().subSequence(0,1));
//     holder.itemCatTitle.setText(itemCategoryData.get(position).getItem_category_name());


//        holder.fTitle.setText(itemCategoryData.get(position).getItem_category_name().subSequence(0,1));
//        holder.itemCatTitle.setText(itemCategoryData.get(position).getItem_category_name());
//
//     holder.itemCatTitle.setText(productCat.getTitle());
//     holder.fTitle.setText(productCat.getTitle().subSequence(0,2));
//        GradientDrawable drawable = (GradientDrawable) holder.fTitle.getBackground();
//            Random randomBackgroundColor = new Random();
//            int color = Color.argb(255,randomBackgroundColor.nextInt(256),randomBackgroundColor.nextInt(256),randomBackgroundColor.nextInt(256));
//            drawable.setColor(color);

//            holder.Position = position;


    }

    @Override
    public int getItemCount() {
//return Aitem == null ? 0 : Aitem.size();
//        return Aitem.size();
        return itemCategoryData == null ? 0 : itemCategoryData.size();
//        return productModelCat == null ? 0 : productModelCat.size();
    }

    public class ItemCategoryHolder extends RecyclerView.ViewHolder {

        private TextView fTitle;
        private AppCompatTextView itemCatTitle;
        private int Position;

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
