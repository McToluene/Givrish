package com.example.givrish.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.example.givrish.ui.ItemDetailsFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder> {
  private List<AllItemsResponseData> allItemsResponseData;
  private  LayoutInflater inflater;
  private Context context;

  public ListItemAdapter(Context context) {
    inflater = LayoutInflater.from(context);
    this.context = context;
  }

  @NonNull
  @Override
  public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =   inflater.inflate(R.layout.item_card, parent, false);
    return new ListItemHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
    AllItemsResponseData item = allItemsResponseData.get(position);

    String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";

    if (item.getItem_images().size() != 0 && item.getItem_images().get(0).getItemLargeImageName() != null) {
      String uri =  url + item.getItem_images().get(0).getItemSmallImageName();
      Picasso.get().load(uri).resize(100, 100).centerCrop().placeholder(R.drawable.download).into( holder.itemImage);

    } else {
      holder.itemImage.setImageResource(R.drawable.download);
    }

    holder.title.setText(item.getItem_title());
    holder.position = position;
  }

  @Override
  public int getItemCount() {
    if (allItemsResponseData != null)
      return allItemsResponseData.size();
    else
      return 0;
  }

  public void setAllItemsResponseData(List<AllItemsResponseData> responseDataList) {
    allItemsResponseData = responseDataList;
  }

  class ListItemHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final ImageView itemImage;
    private final TextView location;
    private int position;

    ListItemHolder(@NonNull View itemView) {
      super(itemView);

      title = itemView.findViewById(R.id.tv_title);
      itemImage = itemView.findViewById(R.id.item_image);
      location = itemView.findViewById(R.id.tv_location);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle = new Bundle();
          bundle.putInt("POSITION", position);
          ItemDetailsFragment itemDetails = new ItemDetailsFragment();
          itemDetails.setArguments(bundle);
          loadDetail(itemDetails);
        }

        private void loadDetail(Fragment itemDetails) {
          FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
          transaction.replace(R.id.dashboard_layout, itemDetails);
          transaction.addToBackStack(null);
          transaction.commit();
        }
      });
    }

  }
}
