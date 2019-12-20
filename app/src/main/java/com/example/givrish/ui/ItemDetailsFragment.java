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
import com.example.givrish.viewmodel.ItemDetailsViewModel;


public class ItemDetailsFragment extends Fragment {

  private ItemDetailsViewModel mViewModel;
  private TextView prodNameTextView;
  private TextView prodLocationTextView;



  public static ItemDetailsFragment newInstance() {
    return new ItemDetailsFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    int position = getArguments().getInt("POSITION");
    Log.i("POSITION", Integer.toString(position));



    return inflater.inflate(R.layout.item_details_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
    // TODO: Use the ViewModel




  }

}
