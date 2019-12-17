package com.example.givrish.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.givrish.R;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.models.ProductModel;

import java.util.List;

public class ListFragment extends Fragment {

  private ListViewModel mViewModel;

  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {


    return inflater.inflate(R.layout.fragment_list_item, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    // TODO: Use the ViewModel


    List <ProductModel> productsList = ProductModel.createProduct();
    Log.i("Products", productsList.toString());

    RecyclerView listRecyclerView = getActivity().findViewById(R.id.listItem);
    listRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    listRecyclerView.setAdapter( new ListItemAdapter(productsList, getContext()));


  }

}
