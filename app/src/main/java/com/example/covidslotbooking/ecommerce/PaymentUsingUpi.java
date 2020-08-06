package com.example.covidslotbooking.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidslotbooking.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PaymentUsingUpi extends AppCompatActivity {
    TextInputEditText name,address,phoneno;
    Button button;
    final int PAY_REQUEST = 1;
    String url = "http://192.168.43.181/phpmyadmin/savepaymentdetails.php";
    String productprice,productname;
    Integer quant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_using_upi);
        Intent intent = getIntent();
         productname = intent.getStringExtra("productname");
         productprice = intent.getStringExtra("productprice");
         quant = intent.getIntExtra("quant",1);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phoneno = findViewById(R.id.phoneno);
        button = findViewById(R.id.payusingupibtn);
        button.setOnClickListener(v -> {
            //todo enter upi id
            if(checkfields()) {
                //if (productprice != null) {
                int productpriceinteger = Integer.parseInt(productprice);
                String amount = String.valueOf(productpriceinteger * quant);
                Log.e("data", productname + " " + amount + " " + name.getText().toString() + " " + address.getText().toString() + " " + phoneno.getText().toString());
             //  handleSSLHandshake();
               // savedetailsindatabase();
                PayUsingUpi(name.getText().toString().trim(), "ENTER UPI ID ", amount, "payment for pack", "trans id", "ref id");
            }
          //  }
        });

    }

    private boolean checkfields() {
        if(name.getText().toString().isEmpty()){
            name.setError("name can't be empty");
            name.requestFocus();
            return false;
        }
        if(address.getText().toString().isEmpty()) {
            address.setError("address can't be empty");
            address.requestFocus();
            return false;
        }
        if(phoneno.getText().length()<=2){
            phoneno.setError("Enter your Phone number");
            return false;
        }
        if(!Patterns.PHONE.matcher(phoneno.getText()).matches()){
            phoneno.setError("please enter a valid phone number");
            return false;
        }
        return true;
    }

    private void PayUsingUpi(String name,String upiId,String amt,String msg, String trnId, String refId){
        Uri uri = new Uri.Builder()
                .scheme("upi").authority("pay")
                .appendQueryParameter("pa",upiId)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("tn",msg)
                .appendQueryParameter("am",amt)
                .appendQueryParameter("tid",trnId)
                .appendQueryParameter("tr",refId)
                .appendQueryParameter("cu","INR")
                .build();

        Intent upiIntent = new Intent(Intent.ACTION_VIEW);
        upiIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiIntent,"Pay");
        if(chooser.resolveActivity(getPackageManager()) != null){
            startActivityForResult(chooser,PAY_REQUEST);
        }else{
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAY_REQUEST){

            if(isInternetAvailabe(getApplicationContext())){

                if (data == null) {
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    String temp = "nothing";
                    Toast.makeText(this, "Transaction not complete", Toast.LENGTH_SHORT).show();
                }else {
                    String text = data.getStringExtra("response");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(text);

                    upiPaymentCheck(text);
                }
            }

        }
    }

    void upiPaymentCheck(String data){
        String str = data;

        String payment_cancel = "";
        String status = "";
        String response[] = str.split("&");

        for (int i = 0; i < response.length; i++)
        {
            String equalStr[] = response[i].split("=");
            if(equalStr.length >= 2)
            {
                if (equalStr[0].toLowerCase().equals("Status".toLowerCase()))
                {
                    status = equalStr[1].toLowerCase();
                }
            }
            else
            {
                payment_cancel = "Payment cancelled";
            }
        }
        if(status.equals("success")){
            savedetailsindatabase();
            Toast.makeText(this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
        }else if("Payment cancelled".equals(payment_cancel)){
            Toast.makeText(this, "payment cancelled by user", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void savedetailsindatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.e("in try","in try");
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    Log.e("json",success);
                    if(success.equals("1")){
                        Log.e("sucessfull","recieved success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("in error response",error+" ");
                Toast.makeText(getApplicationContext(),
                        "Make sure You have an active Internet connection and try again ",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name.getText().toString());
                params.put("address",address.getText().toString());
                params.put("phoneno",phoneno.getText().toString());
                params.put("prodcutname",productname);
                params.put("productprice",productprice);
                params.put("productquant",quant.toString());
                Log.e("map data",params+" ");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public static boolean isInternetAvailabe(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo.isConnected() && networkInfo.isConnectedOrConnecting() && networkInfo.isAvailable()){
                return true;
            }
        }
        return false;
    }
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}