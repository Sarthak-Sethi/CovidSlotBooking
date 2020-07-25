package com.example.covidslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.covidslotbooking.fragments.HomeFragment;
import com.example.covidslotbooking.fragments.Servicesragment;
import com.example.covidslotbooking.fragments.kitFragment;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.drawyerlayout);
        navigationView = findViewById(R.id.side_nav_view);
        toolbar = findViewById(R.id.toolbar);
        // use our tool bar
      //  setSupportActionBar(toolbar);
    navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle
                (this,drawerLayout,toolbar,
                        R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.more);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);
        



//        if(savedInstanceState == null){
//            chipNavigationBar.setItemSelected(R.id.home,true);
//            HomeFragment homeFragment = new HomeFragment();
//            fragmentManager.beginTransaction().replace(R.id.fragment,homeFragment).commit();
//        }
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.service:
                        fragment = new Servicesragment();
                        break;
                    case R.id.kit:
                        fragment = new kitFragment();
                        break;
                }
                if(fragment!=null){
                   fragmentManager = getSupportFragmentManager();
                   fragmentManager.beginTransaction().replace(R.id.fragment,fragment).commit();
                }
                else {
                    Log.e("SARTHAK","error in fragment");
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        super.onBackPressed();
    }
    //@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.profile :
//                Intent intent1 = new Intent(getApplicationContext(),UserProfile.class);
//                startActivity(intent1);
//                break;
//            case  R.id.aboutus :
//                Intent i = new Intent(getApplicationContext(),Aboutus.class);
//                startActivity(i);
//                break;
//            case R.id.service :
//                Intent intent = new Intent(getApplicationContext(),Services.class);
//                startActivity(intent);
//                break;
//            case R.id.ourproducts:
//                Intent intent3 = new Intent(getApplicationContext(),Products.class);
//                startActivity(intent3);
//                break;
//            case R.id.helpcenter:
//                Intent intent4 = new Intent(getApplicationContext(),Helpcenter.class);
//                startActivity(intent4);
//                break;
//            case R.id.updates:
//                checkupdates();
//                break;
//            case R.id.shareapp:
//               shareapp();
//                break;
//            case R.id.logout:
//                logout();
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void logout() {
        Intent i = new Intent(getApplicationContext(),login.class);
        startActivity(i);
    }

    private void checkupdates() {

    }

    private void shareapp() {
        Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("text/plain");
        String share = "link to share added here ";
        shareintent.putExtra(Intent.EXTRA_TEXT,share);
        startActivity(Intent.createChooser(shareintent,"Share app using "));
    }
}