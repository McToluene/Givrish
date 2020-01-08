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
import com.example.givrish.ui.AddItemFragment;
import com.squareup.picasso.Picasso;

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
        holder.txtList.setText(item.getItem_title().toUpperCase());
        holder.txtLocation.setText(item.getItem_address());

       /* String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";
       // String uri =  url + ;

        if(URLUtil.isValidUrl(uri)) {
            Picasso.get().load(uri).resize(100, 100).centerCrop().placeholder(R.drawable.download).into(holder.itemImage);
        } else {
            holder.productImgAdp.setImageResource(R.drawable.download);
        }
*/
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
                    Toast.makeText(context, "need to work on it", Toast.LENGTH_SHORT).show();

                   /* GetUserItemResponseData clickItem = allItemsResponseData.get(getAdapterPosition());
                    ItemModel itemModel=new ItemModel(clickItem.getUser_id(), clickItem.getItem_title(), clickItem.getItem_color(), clickItem.getItem_description(), clickItem.getItem_category_id(), clickItem.getItem_sub_category_id());
                    AddItemFragment addItemFragment=AddItemFragment.newParcelableInstance(itemModel);
                    loadDetail(addItemFragment);*/

                   //TODO: profile pic back pic, recycler item menu list, remove appbar icons, add setting and update to logout
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
