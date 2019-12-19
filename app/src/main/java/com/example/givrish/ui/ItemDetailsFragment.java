package com.example.givrish.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.givrish.R;
import com.example.givrish.models.ProductModel;
import com.example.givrish.viewmodel.ItemDetailsViewModel;

import java.util.ArrayList;

public class ItemDetailsFragment extends Fragment {

  private ItemDetailsViewModel mViewModel;
  private TextView prodNameTextView;
  private TextView prodLocationTextView;
  String prodLocation;
  String productName;

  public static ItemDetailsFragment newInstance() {
    return new ItemDetailsFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    int position = getArguments().getInt("POSITION");
    Log.i("POSITION", Integer.toString(position));


    //    getting the data to show its details
    //get the textView clicked
    String item = getArguments().getString("itemClick");
    //instantiate the products
    ArrayList<ProductModel> pr = ProductModel.createProduct();
    //get all titles
    ArrayList<String> title=ProductModel.getAllTitle();
    //get the location of the item click in the title list
    int newLoc=title.indexOf(item);
    //populate it details using the location
    ProductModel productModels=pr.get(newLoc);
    prodLocation=productModels.getLocation();
    productName=productModels.getTitle();

    return inflater.inflate(R.layout.item_details_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
    // TODO: Use the ViewModel


    //setting the item data
    prodNameTextView=getActivity().findViewById(R.id.txtProductNameDetail);
    prodLocationTextView=getActivity().findViewById(R.id.txtProductLocationDetail);

    prodNameTextView.setText(productName);
    prodLocationTextView.setText(prodLocation);

  }

}
