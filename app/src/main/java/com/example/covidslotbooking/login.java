package com.example.covidslotbooking;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.regex.Pattern;

public class login extends AppCompatActivity {
TextInputEditText phonenumber;
TextInputEditText password;
TextView signupnewuser;
Button loginbtn;
String url = "https://127.0.0.1/phpmyadmin/login.php?phoneno=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();
       loginbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(checkfields()){
                   verifydetailsandlogin();
               }
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
        signupnewuser = findViewById(R.id.link_signup);
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