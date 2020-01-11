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
import com.example.givrish.interfaces.ICategoriesListener;

import java.util.List;
import java.util.Random;

public class ItemSubCategoryAdapter extends RecyclerView.Adapter<ItemSubCategoryAdapter.ItemSubCategoryHolder> {
  private Context mContext;
  private List<ItemSubCategoryData> itemSubCategoryData;
  private LayoutInflater inflater;
  private ICategoriesListener listener;



  public ItemSubCategoryAdapter(Context mContext) {
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);
    listener = (ICategoriesListener) mContext;
  }


  @NonNull
  @Override
  public ItemSubCategoryAdapter.ItemSubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemSubCategoryview = inflater.inflate(R.layout.item_sub_category,parent,false);
    return new ItemSubCategoryHolder(itemSubCategoryview);
  }

  @Override
  public void onBindViewHolder(@NonNull ItemSubCategoryAdapter.ItemSubCategoryHolder holder, int position) {
    ItemSubCategoryData data = itemSubCategoryData.get(position);
    holder.itemSubCatTitle.setText(data.getItem_sub_category_name());
    holder.fSubTitle.setText(data.getItem_sub_category_name().subSequence(0,1));
    holder.position = position;

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

  class ItemSubCategoryHolder extends RecyclerView.ViewHolder {
    private TextView fSubTitle;
    private AppCompatTextView itemSubCatTitle;
    private int position;

    ItemSubCategoryHolder(@NonNull View itemView) {
      super(itemView);
      fSubTitle = itemView.findViewById(R.id.sub_tvv);
      itemSubCatTitle = itemView.findViewById(R.id.sub_tv_name_v);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          listener.filterList(itemSubCategoryData.get(position).getItem_sub_category_id());
        }
      });
    }
  }
}
