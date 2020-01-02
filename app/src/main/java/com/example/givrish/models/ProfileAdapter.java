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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private ArrayList<String> listString;
    private Context context;
    private ArrayList<ProductModel> productModels;
    private ProductModel gottenItem;

    public ProfileAdapter(ArrayList<String> listStr, Context context) {
        listString = listStr;
        this.context = context;
        productModels=ProductModel.createProduct();
        gottenItem=new ProductModel();
    }


    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gift_list_row, parent, false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String prodName=listString.get(position);
        holder.txtList.setText(prodName);
        holder.position = position;

        int prodPosition=listString.indexOf(prodName);
        gottenItem=productModels.get(prodPosition);
        holder.txtLocation.setText(gottenItem.getItem_Location());
    }


    @Override
    public int getItemCount() {
        return listString.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //declered variables
        TextView txtList;
        private int position;
        ImageView productImgAdp;
        TextView txtLocation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtList = itemView.findViewById(R.id.txtProdNameMyProfile);
            productImgAdp = itemView.findViewById(R.id.imgProdProfileEdit);
            txtLocation = itemView.findViewById(R.id.txtProdLocMyProfile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Bundle bundle = new Bundle();
                    bundle.putInt("POSITION", position);
                    bundle.putString("itemClick", txtList.getText().toString());
                    ItemDetailsFragment itemDetails = new ItemDetailsFragment();
                    itemDetails.setArguments(bundle);
                    loadDetail(itemDetails);*/
                }
            });
        }
    }

    private void loadDetail(Fragment itemDetails) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.search_layout, itemDetails);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
