package com.example.givrish.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.interfaces.ItemSelectedListener;
import com.example.givrish.ui.ItemDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder> {
  private List<AllItemsResponseData> allItemsResponseData;
  private  LayoutInflater inflater;
  private Context context;
  private ItemSelectedListener listener;
  private double latitude;
  private double longitude;
  private double gottenDistance;

  public ListItemAdapter(Context context) {
    inflater = LayoutInflater.from(context);
    this.context = context;
    listener = (ItemSelectedListener) context;
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
      Picasso.get().load(uri).resize(150, 150).placeholder(R.drawable.download).into( holder.itemImage);

    } else {
      holder.itemImage.setImageResource(R.drawable.download);
    }
    holder.title.setText(item.getItem_title());
    holder.position = position;

    gottenDistance = getDistance(latitude, longitude, spliceLocation(item.getItem_latitude()), spliceLocation(item.getItem_longitude()), "K");
    int distance = (int) gottenDistance;
    String appendDistance = distance + " km";
    holder.location.setText(appendDistance);
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
    notifyDataSetChanged();
  }

  private static double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
      return 0;
    }
    else {
      double theta = lon1 - lon2;
      double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
      dist = Math.acos(dist);
      dist = Math.toDegrees(dist);
      dist = dist * 60 * 1.1515;
      if (unit.equals("K")) {
        dist = dist * 1.609344;
      } else if (unit.equals("N")) {
        dist = dist * 0.8684;
      }
      return (dist);
    }
  }

  public void setLongitude(String lng) {
    try {
      this.longitude = Double.parseDouble(lng);
    } catch (Exception ex) {

      if (ex.getMessage() != null)
        Log.i("FAILED CONVERSION", ex.getMessage());
      else
        Log.i("FAILED CONVERSION", "Conversion not allowed");
    }

  }

  public void setLatitude(String lat) {
    this.latitude = Double.parseDouble(lat);
  }

  private Double tryParse(String item) {
    double result = 0.0D;
    try {
      result = Double.parseDouble(item);
    } catch (Exception ex) {

      if (ex.getMessage() != null)
        Log.i("FAILED CONVERSION", ex.getMessage());
      else
        Log.i("FAILED CONVERSION", "Conversion not allowed");

    }
    return result;
  }

  private Double spliceLocation(String item){
    String result = "";
    if ((item != null) && (item.length() > 0)) {
      if (item.contains("s") || item.contains("e") || item.contains("w") || item.contains("n"))
        result = item.substring(0, item.length() - 1);
    }

    return tryParse(result);
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
          AllItemsResponseData selectedItem = allItemsResponseData.get(position);
          ItemDetailsFragment itemDetails = ItemDetailsFragment.newInstance(selectedItem, gottenDistance );
          listener.loadItem(itemDetails, ItemDetailsFragment.ITEM_DETAILS_TAG);
        }
      });
    }

  }
}
