package com.example.givrish.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.givrish.R;
import com.example.givrish.viewmodel.AddItemViewModel;

public class AddItemFragment extends Fragment {

  private AddItemViewModel mViewModel;

  public static AddItemFragment newInstance() {
    return new AddItemFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.add_item_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
    // TODO: Use the ViewModel
  }

}
