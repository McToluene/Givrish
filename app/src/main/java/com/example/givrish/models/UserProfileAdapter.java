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
    boolean check=true;

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

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    public void setUserItemsResponseData(List<GetUserItemResponseData> getUserItemResponseData) {
        if(check==true || getUserItemResponseData.size()>1) {
            allItemsResponseData = getUserItemResponseData;
            ITEM_COUNT=getUserItemResponseData.size();
            check=false;
            notifyDataSetChanged();
        }else if(check == true && getUserItemResponseData.size()==1){
            allItemsResponseData = getUserItemResponseData;
            ITEM_COUNT=getUserItemResponseData.size();
            check=false;
            notifyDataSetChanged();
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
                    ItemModel itemModel=new ItemModel(clickItem.getUser_id(), clickItem.getItem_title(), clickItem.getItem_color(), clickItem.getItem_description(), clickItem.getItem_category_id(), clickItem.getItem_sub_category_id());
                    AddItemFragment addItemFragment=AddItemFragment.newParcelableInstance(itemModel);

                    //TODO: profile pic back pic, recycler item menu list, remove appbar icons, add setting and update to logout
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
