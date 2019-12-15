package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.givrish.ui.AddItemFragment;
import com.example.givrish.ui.FavouritesFragment;
import com.example.givrish.ui.ListFragment;
import com.example.givrish.ui.MessagesFragment;
import com.example.givrish.ui.RequestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

  BottomNavigationView bottomNavigationView;
  private final ListFragment listFragment = new ListFragment();
  private final FavouritesFragment favouritesFragment = new FavouritesFragment();
  private final MessagesFragment messagesFragment = new MessagesFragment();
  private final RequestsFragment requestsFragment = new RequestsFragment();
  private final AddItemFragment addItemFragment = new AddItemFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    bottomNavigationView = findViewById(R.id.navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);

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
        loadFragments(listFragment);
       return true;

      case R.id.navigation_favorite:
        loadFragments(favouritesFragment);
        return true;

      case R.id.navigation_messages:
        loadFragments(messagesFragment);
        return true;

      case R.id.navigation_request:
        loadFragments(requestsFragment);
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
