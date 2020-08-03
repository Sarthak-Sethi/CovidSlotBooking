package com.example.covidslotbooking.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidslotbooking.Adapter;
import com.example.covidslotbooking.R;
import com.example.covidslotbooking.item;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements Adapter.onClick {
    private ArrayList<item> arrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view  =  inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        item item1 = new item("TSR COVID 19 CENTER (HOTEL) ",getString(R.string.desc1));
        item item2 = new item("HOME QUARANTINE PROGRAM",getString(R.string.desc2));
        item item3 = new item("COVID HOMECARE KIT",getString(R.string.desc3));
        arrayList.add(item1);
        arrayList.add(item2);
        arrayList.add(item3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new Adapter(arrayList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onClick(int position) {
        Log.e("duo",position+"");
        if (position == 0|| position ==1 || position == 2){
            intentforgoogleduo();
        }
    }
    public void intentforgoogleduo(){
        Log.e("duo","fn called");
        Intent intent = new Intent();
        intent.setPackage("com.google.android.apps.tachyon.ContactsVideoActionActivity");
        intent.setAction("com.google.android.apps.tachyon.action.CALL");
        intent.setData(Uri.parse("tel:9964906768"));
        startActivity(intent);
    }
//    public void onClick(int position) {
//        Log.e("pos",position+" ");
//        if (position == 1){
//            View view = null;
//            Intent opengoogleduo = view.getPackageManager().getLaunchIntentForPackage("duo.app.goo.gl");
//            startActivity(opengoogleduo);
//        }
//    }

}