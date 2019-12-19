package com.example.givrish.ui;

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
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.givrish.R;
import com.example.givrish.models.ProductModel;
import com.example.givrish.models.SearchAdapter;
import com.example.givrish.viewmodel.SearchViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener{

  private SearchViewModel mViewModel;

  //declaring variables
  private SearchView edtSearch;
  private RecyclerView recyclerView;

  private RecyclerView.LayoutManager layoutManager;
  private SearchAdapter myAdapter;

  private ArrayList<String> listString = ProductModel.getAllTitle();


  public static SearchFragment newInstance() {
    return new SearchFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.search_fragment, container, false);
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    // TODO: Use the ViewModel

//    ((AppCompatActivity) getActivity()).getSupportActionBar(toolbar).setDisplayHomeAsUpEnabled(true);
//    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

    ImageButton backButton = getActivity().findViewById(R.id.back_button);
    backButton.setOnClickListener(this);

    recyclerView = getActivity().findViewById(R.id.recyclerSearchList);
    recyclerView.setHasFixedSize(true);

    layoutManager = new LinearLayoutManager(this.getContext());
    recyclerView.setLayoutManager(layoutManager);

    myAdapter = new SearchAdapter(listString, getContext());
    recyclerView.setAdapter(myAdapter);


    // on searching typing:
    edtSearch = getActivity().findViewById(R.id.searchView);

    edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }


      @Override
      public boolean onQueryTextChange(String newText) {

          if(newText.isEmpty()){
            myAdapter.filterChanges(listString);
          }else {
            //new array list that will hold the filtered data
            ArrayList<String> searchList = new ArrayList<>();

            //for in looping through existing elements
            for (String str : listString) {
              //if the existing elements contains the search input
              if (str.toLowerCase().contains(newText.toLowerCase())) {
                //adding the element to filtered list accumulator
                searchList.add(str);
              }
            }

            //calling a method of the adapter class and passing the filtered list
            myAdapter.filterChanges(searchList);
          }
        return true;
      }
    });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
        case R.id.back_button:
     getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            break;
    }
  }
}