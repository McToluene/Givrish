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

import de.hdodenhof.circleimageview.CircleImageView;

public class ListFragment extends Fragment {

  public static final String CATEGORIES_FRAGMENT_FLAG= "4";
  public static final String PROFILE_FRAGMENT_FLAG = "5";
  public static final String SEARCH_FRAGMENT_FLAG = "6";


  private ListViewModel mViewModel;
  private RecyclerView listRecyclerView;
  private CircleImageView profile;
  private Fragment fragment;

  public static ListFragment newInstance() {
    return new ListFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_list_item, container, false);
    Toolbar toolbar = view.findViewById(R.id.list_item_toolbar);
    profile = view.findViewById(R.id.circleImageView_profile);
    listRecyclerView = view.findViewById(R.id.listItem);


    if (getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
    toolbar.setTitle("Givrish");
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
    // TODO: Use the ViewModel

    List <ProductModel> productsList = ProductModel.createProduct();

    listRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    listRecyclerView.setAdapter( new ListItemAdapter(productsList, getContext()));


    profile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragment = new ProfileFragment();
        loadFragment(fragment, PROFILE_FRAGMENT_FLAG);
      }
    });

  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main_toolbar_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.menu_hamburger){
      fragment = new CategoryFragment();
      loadFragment(fragment, CATEGORIES_FRAGMENT_FLAG);
    } else if (item.getItemId() == R.id.search_menu) {
      fragment = new SearchFragment();
      loadFragment(fragment, SEARCH_FRAGMENT_FLAG);
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadFragment(Fragment fragment, String tag) {
    FragmentTransaction transaction;
    if(getActivity() != null) {
      transaction = getActivity().getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.dashboard_layout, fragment, tag);
      transaction.addToBackStack(tag);
      transaction.commit();
    }

  }

}
