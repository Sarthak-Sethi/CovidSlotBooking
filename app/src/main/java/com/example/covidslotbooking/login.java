package com.example.covidslotbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class login extends AppCompatActivity {
TextInputEditText phonenumber;
TextInputEditText password;
Button signupnewuser;
Button loginbtn;
String url = "https://192.168.43.181/phpmyadmin/login.php?phoneno=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();
       loginbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             //  if(checkfields()){
               //    handleSSLHandshake();
                 //  verifydetailsandlogin();
                   Intent i = new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(i);
              // }
           }
       });
       signupnewuser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(),Signup.class);
               startActivity(i);
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

    private void verifydetailsandlogin() {
        url= url+phonenumber.getText().toString().trim()+"&password="+
                password.getText().toString().trim();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Log.e("URL",url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SARTHAK","in response ");
                if (response.equals("loginsucess")){
                    Toast.makeText(login.this, response+"SUCCESSFULL", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else {
                    Log.e("SARTHAK",response);
                    Toast.makeText(login.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    queue.add(stringRequest);
    }

    public void initialise(){
        signupnewuser = findViewById(R.id.signupbtn);
        phonenumber = findViewById(R.id.phoneno);
        password = findViewById(R.id.edittxtpassword);
        loginbtn = findViewById(R.id.loginbtn);
    }

    public boolean checkfields(){
    String phoneno = phonenumber.getText().toString().trim();
    if(phoneno.isEmpty()){
        phonenumber.setError("Field cant be empty");
        return false;
    }
    if(!Patterns.PHONE.matcher(phoneno).matches()){
        phonenumber.setError("please enter a valid phone number");
        return false;
    }
    return true;
    }
}