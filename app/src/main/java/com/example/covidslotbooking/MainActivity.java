package com.example.covidslotbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.covidslotbooking.fragments.HomeFragment;
import com.example.covidslotbooking.fragments.Servicesragment;
import com.example.covidslotbooking.fragments.kitFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    Menu topmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.bottom_nav);
       // topmenu = findViewById(R.menu.topmenu);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile :
                shareapp();
                break;
            case  R.id.aboutus :
                //Intent i = new Intent(getApplicationContext(),aboutus.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareapp() {
    }
}