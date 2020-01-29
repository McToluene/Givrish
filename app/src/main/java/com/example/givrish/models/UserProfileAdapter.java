package com.example.givrish.models;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.Dashboard;
import com.example.givrish.R;
import com.example.givrish.database.Constants;
import com.example.givrish.ui.AddItemFragment;
import com.example.givrish.ui.MenuProfileDialog;
import com.example.givrish.ui.ProfileFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static com.example.givrish.database.Constants.*;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolder> {

    private Context context;

    public UserProfileAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_gift_list_row, parent, false);
        return new ViewHolder(v);
    }

    GetUserItemResponseData item;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
                item = allItemsResponseData.get(position);
                holder.txtList.setText(item.getItem_title().toUpperCase());
                holder.txtLocation.setText(item.getItem_address());

                String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";

                if (item.getItem_images().size() != 0 && item.getItem_images().get(0).getItemLargeImageName() != null) {
                    String uri = url + item.getItem_images().get(0).getItemSmallImageName();
                    try {
                        Picasso.get().load(uri).fit().placeholder(R.drawable.download).into(holder.productImgAdp);
                    } catch (Exception e) {
                        holder.productImgAdp.setImageResource(R.drawable.download);
                    }
                } else {
                    holder.productImgAdp.setImageResource(R.drawable.download);
                }
                ProfileFragment.txtItemCount.setText(String.valueOf(ITEM_COUNT_MORE));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT_MORE;
    }

    public void setUserItemsResponseData(List<GetUserItemResponseData> getUserItemResponseData) {
        int count=getUserItemResponseData.size();
        if(count==1 && ITEM_COUNT_MORE==1 && COME_ONE==false && (allItemsResponseData==null || count>allItemsResponseData.size())){
            allItemsResponseData = getUserItemResponseData;
            COME_ONE=true;
            if(IS_MORE_ITEM==false)
                notifyDataSetChanged();
        }else if(ITEM_COUNT_MORE>1 && IS_MORE_ITEM==false && (allItemsResponseData==null || count > allItemsResponseData.size())) {
            allItemsResponseData = getUserItemResponseData;
            allItemsResponseData=getUserItemResponseData;
            IS_MORE_ITEM=true;
            if(COME_ONE==false){
                notifyDataSetChanged();
                COME_ONE=true;
            }
        }
        else if(count==1 && ITEM_COUNT_MORE==0){
            ITEM_COUNT_MORE=count;
        }else if(count>1){
            ITEM_COUNT_MORE=count;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //declared variables
        TextView txtList;
        ImageView productImgAdp;
        TextView txtLocation;
        private final ImageButton btnItemMenu;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtList = itemView.findViewById(R.id.txtProdNameMyProfile);
            productImgAdp = itemView.findViewById(R.id.imgProdProfileEdit);
            txtLocation = itemView.findViewById(R.id.txtProdLocMyProfile);

            btnItemMenu =itemView.findViewById(R.id.imgBtnUserItemList);
            btnItemMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgBtnUserItemList:
                    GetUserItemResponseData clickItem = allItemsResponseData.get(getAdapterPosition());
                    ItemModel itemModel=new ItemModel(clickItem.getUser_id(), clickItem.getItem_title(), clickItem.getItem_color(), clickItem.getItem_description(), clickItem.getItem_category_id(), clickItem.getItem_sub_category_id(), clickItem.getItem_images());
                    showDialog(itemModel);
                   break;
            }
        }
    }

    void showDialog(ItemModel itemModel) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = MenuProfileDialog.newInstance(itemModel);
        newFragment.show(transaction, Dashboard.USER_ITEM_MENU_DIALOG);
    }
}
