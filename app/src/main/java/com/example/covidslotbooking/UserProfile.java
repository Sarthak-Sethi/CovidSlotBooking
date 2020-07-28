package com.example.covidslotbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UserProfile extends AppCompatActivity {
    TextInputEditText name,prevphoneno,phoneno,password;
    Button update;
    ImageView imageView;
    String url = "https://192.168.43.181/phpmyadmin/updateprofile.php?name=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.settings_full_name);
        prevphoneno = findViewById(R.id.prevphoneno);
        phoneno = findViewById(R.id.phoneno);
        password = findViewById(R.id.phoneno);
        update = findViewById(R.id.setting_update_btn);
        imageView = findViewById(R.id.setting_profile_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfields()){
                    handleSSLHandshake();
                    verifydetailsandupdate();
                }
            }
        });

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
    private void verifydetailsandupdate() {
        url= url+name.getText().toString().trim()+"&password="+
                password.getText().toString().trim()+"&phoneno="+
                phoneno.getText().toString().trim()+"&prevphoneno="+
                prevphoneno.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Log.e("URL",url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SARTHAK","in response ");
                if (response.equals("Succesfully update")){
                    Toast.makeText(UserProfile.this, response+"SUCCESSFULL", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("SARTHAK",response);
                    Toast.makeText(UserProfile.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }
    private boolean checkfields(){
        if(name.getText().toString().isEmpty()){
            name.setError("name can't be empty");
            name.requestFocus();
            return false;
        }

        if(password.getText().toString().isEmpty()){
            password.setError("password can't be empty");
            password.requestFocus();
            return false;
        }
        if(prevphoneno.getText().toString().isEmpty()){
            prevphoneno.setError(" Previous phone number can't be empty");
            prevphoneno.requestFocus();
            return false;
        }
        if(phoneno.getText().toString().isEmpty()){
            phoneno.setError("phoneno can't be empty");
            phoneno.requestFocus();
            return false;
        }
    return true;
    }
}