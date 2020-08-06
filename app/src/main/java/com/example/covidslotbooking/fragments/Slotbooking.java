package com.example.covidslotbooking.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.covidslotbooking.ConfirmSlotBooking;
import com.example.covidslotbooking.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Slotbooking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Slotbooking extends Fragment {
RadioButton day5,day7,day10;
RadioGroup radioGroup;
ImageView imageView;
Button slotbookingbtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Slotbooking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Slotbooking.
     */
    // TODO: Rename and change types and number of parameters
    public static Slotbooking newInstance(String param1, String param2) {
        Slotbooking fragment = new Slotbooking();
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

        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_slotbooking, container, false);
        day5 = view.findViewById(R.id.day5radiobton);
        day7 = view.findViewById(R.id.day7radiobton);
        day10 = view.findViewById(R.id.day10radiobton);
        radioGroup = view.findViewById(R.id.radiopgroup);
        imageView = view.findViewById(R.id.imageview);
//        if(day5.isChecked() || day7.isChecked() || day10.isChecked()){
//            onRadioButtonClicked(view);
//        }
        day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day5.setTextColor(Color.WHITE);
                day7.setTextColor(Color.BLACK);
                day10.setTextColor(Color.BLACK);
            }
        });
        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day5.setTextColor(Color.BLACK);
                day7.setTextColor(Color.WHITE);
                day10.setTextColor(Color.BLACK);
            }
        });
        day10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day5.setTextColor(Color.BLACK);
                day7.setTextColor(Color.BLACK);
                day10.setTextColor(Color.WHITE);
            }
        });
        slotbookingbtn = view.findViewById(R.id.slotbookibgbtn);
        slotbookingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getContext(), ConfirmSlotBooking.class);
               startActivity(i);
            }
        });
        return view;
    }

}