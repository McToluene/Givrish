package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.givrish.ProfileEditFragment;
import com.example.givrish.R;
import com.example.givrish.models.ProductModel;
import com.example.givrish.models.ProfileAdapter;
import com.example.givrish.models.SearchAdapter;
import com.example.givrish.viewmodel.ProfileViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener{

  private ProfileViewModel mViewModel;

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private ProfileAdapter myAdapter;

  private ArrayList<String> listString;

  private Button btnEditProf;
  private TextView txtUserNameProfile;


  public static ProfileFragment newInstance() {
    return new ProfileFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view=inflater.inflate(R.layout.profile_fragment, container, false);

    Toolbar toolbar=view.findViewById(R.id.profile_toolbar);
    toolbar.setTitle("My Profile");

    if(getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
    }


    listString = ProductModel.getAllTitle();

    recyclerView = view.findViewById(R.id.recyclerMyGiftOut);
    recyclerView.setHasFixedSize(true);

    layoutManager = new LinearLayoutManager(this.getContext());
    recyclerView.setLayoutManager(layoutManager);

    myAdapter = new ProfileAdapter(listString, getContext());
    recyclerView.setAdapter(myAdapter);

    btnEditProf=view.findViewById(R.id.btnEditProfile);
    btnEditProf.setOnClickListener(this);

    txtUserNameProfile=view.findViewById(R.id.id_username);

    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
    // TODO: Use the ViewModel
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.btnEditProfile:
        ProfileEditFragment editFragment=new ProfileEditFragment();
        Bundle bundle=new Bundle();
        bundle.putString("username", txtUserNameProfile.getText().toString());
        editFragment.setArguments(bundle);

        loadFragment(editFragment, "1");
        break;
    }
  }

  private void loadFragment(Fragment fragment, String tag) {
    FragmentTransaction transaction;
    if (getActivity() != null) {
      transaction = getActivity().getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.dashboard_layout, fragment, tag);
      transaction.addToBackStack(tag);
      transaction.commit();
    }
  }
}
