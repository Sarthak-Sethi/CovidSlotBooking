package com.example.covidslotbooking.ecommerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidslotbooking.CartActivity;
import com.example.covidslotbooking.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;



import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



public class Products extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    String url = "https://dwaipayanatechnologies.com/TSR/fetchpackdetails.php";
    CardView pack1, pack2, pack3;

String productname,productdesc,productprice,productimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.100/phpmyadmin/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        pack1 = findViewById(R.id.pack1);
        pack2 = findViewById(R.id.pack2);
        pack3 = findViewById(R.id.pack3);

        pack1.setOnClickListener(v -> {
            Log.e("SAR","clicked");
//                help.handleSSLHandshake();
          //  fetchpack1details();
            Intent i = new Intent(getApplicationContext(),ProductDetails.class);
            Bundle bundle = new Bundle();
            bundle.putString("productname",productname);
            bundle.putString("productprice",productprice);
            bundle.putString("productdesc",productdesc);
            bundle.putString("productimage",productimage);
            i.putExtras(bundle);
            startActivity(i);
        });
pack2.setOnClickListener(v -> {
    fetchpack2details();
    Intent i = new Intent(getApplicationContext(),ProductDetails.class);
    Bundle bundle = new Bundle();
    bundle.putString("productname",productname);
    bundle.putString("productprice",productprice);
    bundle.putString("productdesc",productdesc);
    bundle.putString("productimage",productimage);
    i.putExtras(bundle);
    startActivity(i);

});
    }
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            Log.e("HANDSHAKE","SSLHANDSHAKE");
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
            HttpsURLConnection.setDefaultHostnameVerifier((arg0, arg1) -> true);
        } catch (Exception ignored) {
        }
    }

    private void fetchpack1details() {
        handleSSLHandshake();
        //todo volley string
        Log.e("SAR","fn called");

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
        //    Log.e("in on respone","test");
   //     Log.e("DATA",response);

            try {
                JSONObject jsonArray=new JSONObject(response.substring(10));
     //           Log.e("a", jsonArray.toString());
                JSONArray packsArray=jsonArray.getJSONArray("packs");
//                Log.e("a", ""+packsArray.getJSONObject(0).getString("p_name"));
//                Log.e("a", ""+packsArray.getJSONObject(0).getString("p_desc"));
//                Log.e("a", ""+packsArray.getJSONObject(0).getString("p_image"));
//                Log.e("a", ""+packsArray.getJSONObject(0).getString("p_price"));
                productname = packsArray.getJSONObject(0).getString("p_name");
                productdesc = packsArray.getJSONObject(0).getString("p_desc");
                productimage = packsArray.getJSONObject(0).getString("p_image");
                productprice = packsArray.getJSONObject(0).getString("p_price");

            } catch (JSONException e) {
                Log.e("hello", e.getMessage());
            }
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                JSONArray jsonArray = jsonObject.getJSONArray("packs");
//                jsonArray.getJSONObject(0);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }, error -> Log.e("volley error",error.toString()));
        requestQueue.add(stringRequest);
        //TODO:- volley json

//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                response -> {
//                    try {
//                        Log.e("TEST","in try");
//                        JSONObject jsonObject = new JSONObject("{packs}");
//                        JSONArray jsonArray = response.getJSONArray("packs");
//                        Log.e("DATA",jsonObject.toString());
//                        Log.e("DATA",jsonObject.getString("p_name"));
//                        for (int i=0;i<jsonArray.length();i++) {
//                            Log.e("DATA",jsonObject.getString("p_name"));
//                        }
//
//                        } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }, error -> Log.e("volley error",error.toString())) ;
//
//        queue.add(jsonObjectRequest);


//        Call<DataTemp> call = jsonPlaceHolderApi.getData();
//        call.enqueue(new Callback<DataTemp>() {
//            @Override
//            public void onResponse(Call<DataTemp> call, Response<DataTemp> response) {
//                if (!response.isSuccessful()) {
//                    Log.e("DATA ERROR 1", "Code: " + response.code());
//                    return;
//                }
//                //dataTemp=response.body();
//                Log.e("data",response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<DataTemp> call, Throwable t) {
//                Log.e("DATA ERROR 2", t.getMessage());
//            }
//        });

    }
    private void fetchpack2details() {
        handleSSLHandshake();
        //todo volley string
        Log.e("SAR","fn called");

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Log.e("in on respone","test");
            Log.e("DATA",response);

            try {
                JSONObject jsonArray=new JSONObject(response.substring(9));
                Log.e("a", jsonArray.toString());
                JSONArray packsArray=jsonArray.getJSONArray("packs");
                Log.e("a", ""+packsArray.getJSONObject(1).getString("p_name"));
                Log.e("a", ""+packsArray.getJSONObject(1).getString("p_desc"));
                Log.e("a", ""+packsArray.getJSONObject(1).getString("p_image"));
                Log.e("a", ""+packsArray.getJSONObject(1).getString("p_price"));
                productname = packsArray.getJSONObject(1).getString("p_name");
                productdesc = packsArray.getJSONObject(1).getString("p_desc");
                productimage = packsArray.getJSONObject(1).getString("p_image");
                productprice = packsArray.getJSONObject(1).getString("p_price");

            } catch (JSONException e) {
                Log.e("hello", e.getMessage());
            }

        }, error -> Log.e("volley error",error.toString()));
        requestQueue.add(stringRequest);


    }
}