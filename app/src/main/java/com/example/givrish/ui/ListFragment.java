package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.givrish.R;
import com.example.givrish.models.ListItemAdapter;
import com.example.givrish.models.ProductModel;
import com.example.givrish.viewmodel.ListViewModel;

import java.util.List;

public class ListFragment extends Fragment {

  private ListViewModel mViewModel;
  private Toolbar toolbar;

  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    toolbar = getActivity().findViewById(R.id.toolbar);
    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    return inflater.inflate(R.layout.fragment_list_item, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    // TODO: Use the ViewModel

    List <ProductModel> productsList = ProductModel.createProduct();

    RecyclerView listRecyclerView = getActivity().findViewById(R.id.listItem);
    listRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    listRecyclerView.setAdapter( new ListItemAdapter(productsList, getContext()));

  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main_toolbar_menu, menu);
  }


  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_hamburger:
        loadCategories();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadCategories() {
    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.dashboard_layout, new CategoryFragment());
    transaction.addToBackStack(null);
    transaction.commit();
  }


}
