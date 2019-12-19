package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.givrish.R;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.models.ProductModel;
import com.example.givrish.viewmodel.ListViewModel;

import java.util.List;

public class ListFragment extends Fragment {

  private ListViewModel mViewModel;
  private Toolbar toolbar;
  private ImageButton categories;

  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    toolbar = getActivity().findViewById(R.id.main_toolbar);
    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


    return inflater.inflate(R.layout.fragment_list_item, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    // TODO: Use the ViewModel

    List <ProductModel> productsList = ProductModel.createProduct();
    categories = getActivity().findViewById(R.id.cat_btn);
    categories.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, new CategoryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });

    RecyclerView listRecyclerView = getActivity().findViewById(R.id.listItem);
    listRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    listRecyclerView.setAdapter( new ListItemAdapter(productsList, getContext()));

  }

}
