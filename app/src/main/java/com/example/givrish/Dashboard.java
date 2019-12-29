package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.ui.AddItemFragment;
import com.example.givrish.ui.FavouritesFragment;
import com.example.givrish.ui.ListFragment;
import com.example.givrish.ui.MessagesFragment;
import com.example.givrish.ui.RequestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Dashboard extends AppCompatActivity implements CallBackListener, BottomNavigationView.OnNavigationItemSelectedListener {

  public static final String LIST_ITEM_FRAGMENT_FLAG = "1";
  public static final String ADD_ITEM_FRAGMENT_FLAG = "2";
  public static final String MESSAGE_FRAGMENT_FLAG = "3";
  public static final String FAVOURITES_FRAGMENT_FLAG = "5";
  public static final String REQUESTS_FRAGMENT_FLAG = "6";


  private static int FLAG = 0;
  BottomNavigationView bottomNavigationView;
  private final AddItemFragment addItemFragment = new AddItemFragment();
  private Fragment fragment = new ListFragment();
  private FloatingActionButton fab;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    bottomNavigationView = findViewById(R.id.navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);
    fab = findViewById(R.id.fab);
    fab.setColorFilter(getResources().getColor(R.color.white));



    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (FLAG == 0) {
          loadFragments(addItemFragment, ADD_ITEM_FRAGMENT_FLAG);
          fab.setImageDrawable(getDrawable(R.drawable.ic_add_box_));
          FLAG = 1;
        } else if (FLAG == 1) {
          AddItemFragment addItemFragment = (AddItemFragment) getSupportFragmentManager().findFragmentByTag(ADD_ITEM_FRAGMENT_FLAG);
          if (addItemFragment != null) addItemFragment.addItem();
        }
      }
    });
    loadFragments(fragment, "1");
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.navigation_home:
        fragment = new ListFragment();
        loadFragments(fragment, LIST_ITEM_FRAGMENT_FLAG);
        fab.setImageDrawable(getDrawable(R.drawable.gift_box));
        FLAG = 0;
        return true;

      case R.id.navigation_favorite:
        fragment = new FavouritesFragment();
        loadFragments(fragment, FAVOURITES_FRAGMENT_FLAG);
        fab.setImageDrawable(getDrawable(R.drawable.gift_box));
        FLAG = 0;
        return true;

      case R.id.navigation_messages:
        fragment = new MessagesFragment();
        loadFragments(fragment, MESSAGE_FRAGMENT_FLAG);
        fab.setImageDrawable(getDrawable(R.drawable.gift_box));
        FLAG = 0;
        return true;

      case R.id.navigation_request:
        fragment = new RequestsFragment();
        loadFragments(fragment, REQUESTS_FRAGMENT_FLAG);
        fab.setImageDrawable(getDrawable(R.drawable.gift_box));
        FLAG = 0;
        return true;
    }
    return false;
  }

  private void loadFragments(Fragment fragment, String tag) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.frame_container, fragment, tag);
    transaction.addToBackStack(tag);
    transaction.commit();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
      fragment.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public void onBackClick(String tag) {
    FragmentManager manager = getSupportFragmentManager();
    manager.popBackStack();
    fab.setImageDrawable(getDrawable(R.drawable.gift_box));
    FLAG = 0;
  }
}
