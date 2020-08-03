package com.example.covidslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidslotbooking.ecommerce.Products;
import com.example.covidslotbooking.fragments.HomeFragment;
import com.example.covidslotbooking.fragments.Slotbooking;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

import static android.os.Build.ID;


public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    Button button;
    NavigationView navigationView;
    Toolbar toolbar;
    private boolean disablebackbutton  = false;

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

        if(savedInstanceState == null){
            chipNavigationBar.setItemSelected(R.id.home,true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment,homeFragment).commit();
        }
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.slotbooking:
                        fragment = new Slotbooking();
                        break;
                    case R.id.caller:
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:+91 9964906768"));
                        startActivity(i);
                        break;
                    default:
                        fragment = new HomeFragment();
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
                switch (item.getItemId()){
                    case R.id.profile :
                        Intent intent1 = new Intent(getApplicationContext(),UserProfile.class);
                        startActivity(intent1);
                        break;
                    case R.id.home:
                        Intent ihome = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(ihome);
                        break;
                    case  R.id.aboutus :
                        Intent i = new Intent(getApplicationContext(),Aboutus.class);
                        startActivity(i);
                        break;
                    case R.id.contactus :
                        Intent intent = new Intent(getApplicationContext(),Contactus.class);
                        startActivity(intent);
                        break;
                    case R.id.ourproducts:
                        Intent intent3 = new Intent(getApplicationContext(), Products.class);
                        startActivity(intent3);
                        break;
                    case R.id.helpcenter:
                        Intent intent4 = new Intent(getApplicationContext(),Helpcenter.class);
                        startActivity(intent4);
                        break;
                    case R.id.updates:
                        checkupdates();
                        break;
                    case R.id.shareapp:
                       shareapp();
                        break;
                    case R.id.logout:
                        disablebackbutton = true;
                        logout();
                        break;
                }
                return false;
            }
        });

    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(disablebackbutton){

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
//                Intent intent = new Intent(getApplicationContext(),Contactus.class);
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