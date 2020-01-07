package com.example.givrish.models;

import android.content.Context;
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
import com.example.givrish.ui.AddItemFragment;

import java.util.List;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolder> {

    private Context context;
    private List<GetUserItemResponseData> allItemsResponseData;

    public UserProfileAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gift_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GetUserItemResponseData item = allItemsResponseData.get(position);
        holder.txtList.setText(item.getItem_title());
        holder.txtLocation.setText(item.getItem_address());

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //declared variables
        TextView txtList;
        ImageView productImgAdp;
        TextView txtLocation;

        ImageView edit, delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtList = itemView.findViewById(R.id.txtProdNameMyProfile);
            productImgAdp = itemView.findViewById(R.id.imgProdProfileEdit);
            txtLocation = itemView.findViewById(R.id.txtProdLocMyProfile);

            edit=itemView.findViewById(R.id.imgEditItem);
            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgEditItem:
                    GetUserItemResponseData clickItem = allItemsResponseData.get(getAdapterPosition());
                    ItemModel itemModel=new ItemModel(clickItem.getUser_id(), clickItem.getItem_title(), clickItem.getItem_color(), clickItem.getItem_description(), clickItem.getItem_category_id(), clickItem.getItem_sub_category_id());
                    AddItemFragment addItemFragment=AddItemFragment.newParcelableInstance(itemModel);
                    loadDetail(addItemFragment);
                    break;
            }
        }
    }

    private void loadDetail(Fragment itemDetails) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_layout, itemDetails);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
