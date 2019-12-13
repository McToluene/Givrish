package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;

    ImageView profileId, menuId;
    EditText searchId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileId = findViewById(R.id.profile_id);
        profileId.setOnClickListener(this);

        menuId = findViewById(R.id.menu_id);
        menuId.setOnClickListener(this);

        searchId = findViewById(R.id.search_id);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.navigation_home:
                Toast.makeText(this, "This is Main menu", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.navigation_favorite:
                Toast.makeText(this, "This is Favorite Menu", Toast.LENGTH_SHORT).show();
                return true;

//            case R.id.nav:
//                Toast.makeText(this, "this is give menu", Toast.LENGTH_SHORT).show();
//                return true;

            case R.id.navigation_messages:
                Toast.makeText(this, "This is Message Menu", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.navigation_request:
                Toast.makeText(this, "This is Request menu", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_id:
                Toast.makeText(this, "My profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_id:
                Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
