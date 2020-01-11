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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.interfaces.ICategoriesListener;

import java.util.List;
import java.util.Random;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ItemCategoryHolder>{
  private List<ItemCategoryData> itemCategoryData;
  private Context context;
  private LayoutInflater inflater;
  private ICategoriesListener listener;

  public static final String SUB_CATEGORIES_FRAGMENT_fLAG = "2001";


  public ItemCategoryAdapter(Context context) {
    this.context = context;
    inflater = LayoutInflater.from(context);
    listener = (ICategoriesListener) context;

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
      fTitle =  itemView.findViewById(R.id.sub_tvv);
      itemCatTitle = itemView.findViewById(R.id.sub_tv_name_v);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          listener.loadSub(itemCategoryData.get(position).getItem_category_id());
        }
      });

    }

  }

}
