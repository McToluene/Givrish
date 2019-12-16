package com.example.givrish.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.givrish.R;
import com.example.givrish.ui.ItemDetailsFragment;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHolder> {
  private List<ProductModel> productModelList;
  private  LayoutInflater inflater;
  private Context context;

  public ListItemAdapter(List<ProductModel> productModelList, Context context) {
    this.productModelList = productModelList;
    inflater = LayoutInflater.from(context);
    this.context = context;
  }

  @NonNull
  @Override
  public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =   inflater.inflate(R.layout.item_card, parent, false);
    ListItemHolder itemHolder = new ListItemHolder(view);
    return itemHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
    ProductModel product = productModelList.get(position);
    holder.title.setText(product.getTitle());
    holder.location.setText(product.getLocation());
  }

  @Override
  public int getItemCount() {
    return productModelList.size();
  }

  public class ListItemHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView location;

    public ListItemHolder(@NonNull View itemView) {
      super(itemView);

      title = itemView.findViewById(R.id.tv_title);
      location = itemView.findViewById(R.id.tv_location);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ItemDetailsFragment itemDetails = new ItemDetailsFragment();
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
