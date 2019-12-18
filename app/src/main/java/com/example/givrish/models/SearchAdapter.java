package com.example.givrish.models;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

  private ArrayList<String> listString;

  public SearchAdapter(ArrayList<String> listStr) {
    listString = listStr;
  }



  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_row, parent, false);

    SearchAdapter.ViewHolder vh=new SearchAdapter.ViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtList.setText(listString.get(position));
  }

  @Override
  public int getItemCount() {
    return listString.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView txtList;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtList = itemView.findViewById(R.id.txtDataRow);
    }
  }

  //This method will filter the list
  //here we are passing the filtered data
  //and assigning it to the list with notifydatasetchanged method
  public void filterChanges(ArrayList<String> searchList) {
    this.listString=searchList;
    notifyDataSetChanged();

  }

}
