package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.givrish.ui.AddItemFragment;
import com.example.givrish.ui.FavouritesFragment;
import com.example.givrish.ui.ListFragment;
import com.example.givrish.ui.MessagesFragment;
import com.example.givrish.ui.ProfileFragment;
import com.example.givrish.ui.RequestsFragment;
import com.example.givrish.ui.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

  BottomNavigationView bottomNavigationView;
  private final AddItemFragment addItemFragment = new AddItemFragment();
  private AppCompatEditText edtSearch;
  private Fragment fragment = new  ListFragment();
  private Toolbar toolbar;
  private ConstraintLayout top;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    bottomNavigationView = findViewById(R.id.navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);


    edtSearch = findViewById(R.id.edt_search);
    toolbar=findViewById(R.id.toolbar);

    //going to search fragment
    edtSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                fragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.dashboard_layout, fragment);
                transaction.addToBackStack("Dashboard");
                transaction.commit();
            }catch (Exception e){
            }
        }
    });

//    CircleImageView profile = findViewById(R.id.profileImageView);
//    profile.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        fragment = new ProfileFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.dashboard_layout, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//      }
//    });

    findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loadFragments(addItemFragment);
      }
    });
    loadFragments(fragment);
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

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	for (Fragment fragment : getSupportFragmentManager().getFragments()) {
		fragment.onActivityResult(requestCode, resultCode, data);
	}
}
}
