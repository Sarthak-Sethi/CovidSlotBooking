package com.example.covidslotbooking.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covidslotbooking.R;

public class ProductDetails extends AppCompatActivity {
Button paymentButton;
NumberPicker numberPicker;
TextView productname,productprice,productdesc;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        String productname = intent.getStringExtra("productname");
        String productprice = intent.getStringExtra("productprice");
        String productimage = intent.getStringExtra("productimage");
        String productdesc = intent.getStringExtra("productdesc");

        imageView = findViewById(R.id.product_image_details);
        Glide.with(getApplicationContext()).load(productimage).into(imageView);
        paymentButton = findViewById(R.id.paymentBtn);
        numberPicker = findViewById(R.id.quantity);
        this.productdesc = findViewById(R.id.product_description_details);
        this.productname = findViewById(R.id.product_name_details);
        this.productprice = findViewById(R.id.product_price_details);

        Log.e("DATA from prev activty",productname+" "+productdesc+" "+productprice+" "+productimage);
        this.productname.setText(productname);
        this.productprice.setText(productprice);
        this.productdesc.setText(productdesc);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

        });

        paymentButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),PaymentUsingUpi.class);
            Bundle bundle = new Bundle();
            bundle.putString("productname",productname);
            bundle.putString("productprice",productprice);
            bundle.putInt("quant", (numberPicker.getValue()));
            i.putExtras(bundle);
            startActivity(i);
        });
    }
}