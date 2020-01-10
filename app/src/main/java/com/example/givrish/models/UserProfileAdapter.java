package com.example.givrish.models;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.database.Constants;
import com.example.givrish.ui.AddItemFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static com.example.givrish.database.Constants.*;

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
        if(allItemsResponseData != null) {
            GetUserItemResponseData item = allItemsResponseData.get(position);
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
        }
    }


    boolean isOneItem=false;
    boolean isMoreItem=false;

    @Override
    public int getItemCount() {
        if(isOneItem==true && ITEM_COUNT_MORE==0)
            return ITEM_COUNT_ONE;
        else if(isMoreItem==true && ITEM_COUNT_ONE>0)
            return ITEM_COUNT_MORE;
        else
            return 0;
    }

    public void setUserItemsResponseData(List<GetUserItemResponseData> getUserItemResponseData) {
        if(isMoreItem==false && getUserItemResponseData.size()>1) {
            allItemsResponseData = getUserItemResponseData;
            ITEM_COUNT_MORE=getUserItemResponseData.size();
          //  notifyDataSetChanged();
            isMoreItem=true;
        }else if(isOneItem == false && getUserItemResponseData.size()==1){
            allItemsResponseData = getUserItemResponseData;
            ITEM_COUNT_ONE = getUserItemResponseData.size();
          //  notifyDataSetChanged();
            isOneItem=true;
        }



    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //declared variables
        TextView txtList;
        ImageView productImgAdp;
        TextView txtLocation;
        private final ImageButton btnItemMenu;
        private ConstraintLayout tvMenu;
        private ImageButton btnEdit, btnDelete, btnViewed;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtList = itemView.findViewById(R.id.txtProdNameMyProfile);
            productImgAdp = itemView.findViewById(R.id.imgProdProfileEdit);
            txtLocation = itemView.findViewById(R.id.txtProdLocMyProfile);

            btnItemMenu =itemView.findViewById(R.id.imgBtnUserItemList);
            tvMenu = itemView.findViewById(R.id.tv_menuShow);
            btnEdit=itemView.findViewById(R.id.btnEditItemProfile);
            btnDelete=itemView.findViewById(R.id.btnDeleteItemProfile);
            btnViewed=itemView.findViewById(R.id.btnViewedItemProfile);

            btnItemMenu.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            btnViewed.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgBtnUserItemList:
                    if(tvMenu.getVisibility()==View.GONE) {
                        tvMenu.setVisibility(View.VISIBLE);
                    }
                    else {
                        tvMenu.setVisibility(View.GONE);
                    }
                    break;
                case R.id.btnEditItemProfile:
                    GetUserItemResponseData clickItem = allItemsResponseData.get(getAdapterPosition());
                    ItemModel itemModel=new ItemModel(clickItem.getUser_id(), clickItem.getItem_title(), clickItem.getItem_color(), clickItem.getItem_description(), clickItem.getItem_category_id(), clickItem.getItem_sub_category_id());
                    AddItemFragment addItemFragment=AddItemFragment.newParcelableInstance(itemModel);
                    loadDetail(addItemFragment);
                    break;
                case R.id.btnViewedItemProfile:
                    Toast.makeText(context, "Go to view details", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnDeleteItemProfile:
                    Toast.makeText(context, "Go to delete confirmation", Toast.LENGTH_SHORT).show();
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
