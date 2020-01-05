package com.example.givrish.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarItemsAdapter extends RecyclerView.Adapter<SimilarItemsAdapter.SimilarItemsViewHolder> {
  private List< AllItemsResponseData> listItems;
  private LayoutInflater inflater;
  private double latitude;
  private double longitude;
  private double gottenDistance;

  public SimilarItemsAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public SimilarItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new SimilarItemsViewHolder(inflater.inflate(R.layout.similar_item_card, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull SimilarItemsViewHolder holder, int position) {
    AllItemsResponseData item = listItems.get(position);
    String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";

    if (item.getItem_images().size() != 0 && item.getItem_images().get(0).getItemLargeImageName() != null) {
      String uri =  url + item.getItem_images().get(0).getItemSmallImageName();
      Picasso.get().load(uri).resize(100, 100).centerCrop().placeholder(R.drawable.download).into( holder.itemImage);

    } else {
      holder.itemImage.setImageResource(R.drawable.download);
    }

    holder.itemName.setText(item.getItem_title());
//    gottenDistance = getDistance(latitude, longitude, spliceLocation(item.getItem_latitude()), spliceLocation(item.getItem_longitude()), "K");
//    int distance = (int) gottenDistance;
//    String appendDistance = distance + " km";
//    holder.itemLocation.setText(appendDistance);

  }

  @Override
  public int getItemCount() {
    return listItems != null ? listItems.size() : 0;
  }

  public void setListItems(List<AllItemsResponseData> listItems) {
    this.listItems = listItems;
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

//    notifyDataSetChanged();
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

  class SimilarItemsViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView itemName, itemLocation;
    AppCompatImageView itemImage;

    SimilarItemsViewHolder(@NonNull View itemView) {
      super(itemView);

      itemName = itemView.findViewById(R.id.tv_similarItemName);
      itemLocation = itemName.findViewById(R.id.tv_similarItemLocation);
      itemImage = itemView.findViewById(R.id.imageView_similarItem);
    }
  }
}
