package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.givrish.ui.AddItemFragment;
import com.example.givrish.ui.FavouritesFragment;
import com.example.givrish.ui.ListFragment;
import com.example.givrish.ui.MessagesFragment;
import com.example.givrish.ui.RequestsFragment;
import com.example.givrish.ui.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

  BottomNavigationView bottomNavigationView;
  private final AddItemFragment addItemFragment = new AddItemFragment();
  private AppCompatEditText edtSearch;
  private Fragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    bottomNavigationView = findViewById(R.id.navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);
    edtSearch = findViewById(R.id.edt_search);
    if(edtSearch.hasFocus()){edtSearch.setCursorVisible(true);}
    edtSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragment = new SearchFragment();
        loadFragments(fragment);
      }
    });

    findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loadFragments(addItemFragment);
      }
    });

  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.navigation_home:
        fragment = new ListFragment();
        loadFragments(fragment);
       return true;

      case R.id.navigation_favorite:
        fragment = new FavouritesFragment();
        loadFragments(fragment);
        return true;

      case R.id.navigation_messages:
        fragment = new MessagesFragment();
        loadFragments(fragment);
        return true;

      case R.id.navigation_request:
        fragment = new RequestsFragment();
        loadFragments(fragment);
        return true;
    }
    return false;
  }

  private void loadFragments(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.frame_container, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

}
