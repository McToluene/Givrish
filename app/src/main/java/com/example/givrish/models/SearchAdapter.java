package com.example.givrish.models;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.ui.ItemDetailsFragment;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

  private ArrayList<String> listString;
  private Context context;

  public SearchAdapter(ArrayList<String> listStr, Context context) {
    listString = listStr;
    this.context = context;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_row, parent, false);

    SearchAdapter.ViewHolder vh=new SearchAdapter.ViewHolder(v);
    return vh;
  }

  ProductModel productModel=new ProductModel();
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String prodName=listString.get(position);
    holder.txtList.setText(prodName);
    holder.position = position;
    //image to be shown
//    productModel.setProductImg(prodName);
//    holder.productImgAdp.setImageResource(productModel.getProductImgResourcesId(holder.productImgAdp.getContext()));
  }

  @Override
  public int getItemCount() {
    return listString.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
      //declered variables
    TextView txtList;
    private int position;
    public ImageView productImgAdp;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtList = itemView.findViewById(R.id.txtDataRow);
      productImgAdp = itemView.findViewById(R.id.imgProd);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle = new Bundle();
          bundle.putInt("POSITION", position);
          bundle.putString("itemClick", txtList.getText().toString());
          ItemDetailsFragment itemDetails = new ItemDetailsFragment();
          itemDetails.setArguments(bundle);
          loadDetail(itemDetails);
        }
      });
    }
  }

  //This method will filter the list
  //here we are passing the filtered data
  //and assigning it to the list with notifydatasetchanged method
  public void filterChanges(ArrayList<String> searchList) {
    this.listString=searchList;
    notifyDataSetChanged();

  }

  private void loadDetail(Fragment itemDetails) {
    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.search_layout, itemDetails);
    transaction.addToBackStack(null);
    transaction.commit();
  }

}
