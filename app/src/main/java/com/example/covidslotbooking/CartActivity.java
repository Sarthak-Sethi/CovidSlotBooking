package com.example.covidslotbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CartActivity extends AppCompatActivity {
    Button proceedtopaymentbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
     proceedtopaymentbtn = findViewById(R.id.proceedtopayment_btn);
     proceedtopaymentbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
     });
    }
}