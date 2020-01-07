package com.example.givrish.models;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.interfaces.ItemSelectedListener;
import com.example.givrish.ui.ItemDetailsFragment;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private List<GetUserItemResponseData> allItemsResponseData;

    public ProfileAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gift_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            GetUserItemResponseData item = allItemsResponseData.get(position);
        holder.txtList.setText(item.getItem_title());
        holder.txtLocation.setText(item.getItem_address());
        holder.position = position;

     /*   String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";

        if (item.getItem_images().size() != 0 && item.getItem_images().get(0).getItemLargeImageName() != null) {
            String uri =  url + item.getItem_images().get(0).getItemSmallImageName();
            Picasso.get().load(uri).resize(100, 100).centerCrop().placeholder(R.drawable.download).into( holder.itemImage);

        } else {
            holder.itemImage.setImageResource(R.drawable.download);
        }*/

    }

    @Override
    public int getItemCount() {
        if (allItemsResponseData != null)
            return allItemsResponseData.size();
        else
            return 0;
    }

    public void setUserItemsResponseData(List<GetUserItemResponseData> getUserItemResponseData) {
        allItemsResponseData = getUserItemResponseData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //declared variables
        TextView txtList;
        ImageView productImgAdp;
        TextView txtLocation;
        private int position;

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


                  /*  AllItemsResponseData selectedItem = allItemsResponseData.get(position);
                    ItemDetailsFragment itemDetails = ItemDetailsFragment.newInstance(selectedItem, gottenDistance );
                    listener.loadItem(itemDetails, ItemDetailsFragment.ITEM_DETAILS_TAG);*/

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
