package com.example.covidslotbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ConfirmSlotBooking extends AppCompatActivity {
    TextInputEditText name,currentaddress,peraddress,phoneno,pincode;
    EditText dateofbooking;
    AutoCompleteTextView city;
    CheckBox agreedtotnc;
    ArrayList cities_list;
    Button confirmslotbtn;
    private static final String apiurl = "https://192.168.43.181/phpmyadmin/slotbooking.php";
    DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_slot_booking);
        initialise();
        fetchcityfromjson();
        dateofbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ConfirmSlotBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                dateofbooking.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
        confirmslotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfields()){
                    handleSSLHandshake();
                    savedataindatabase();
                }
            }
        });
    }
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
    private void savedataindatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.e("in try","in try");
                    Toast.makeText(getApplicationContext(),"Slot Booked sucessfully",Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    Log.e("json",success);
                    if(success.equals("1")){
                        Log.e("sucessfull","recieved success");
                        Toast.makeText(getApplicationContext(),"Registaration Successfull",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.e("in error response",error+" "))
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name.getText().toString());
                params.put("currentaddress",currentaddress.getText().toString());
                params.put("peraddress",peraddress.getText().toString());
                params.put("city",city.getText().toString());
                params.put("pincode",pincode.getText().toString());
                params.put("phoneno",phoneno.getText().toString());
                params.put("dateofbooking",dateofbooking.getText().toString());
                Log.e("map data",params+" ");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public void fetchcityfromjson(){
        Log.e("SARTHAK","injson");
       RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url =  "https://www.json-generator.com/api/json/get/cgoxLceAlK?indent=2";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cities");

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject cities = jsonArray.getJSONObject(i);
                                String city = cities.getString("City");
                                cities_list.add(city);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Occured :  " ,error.getMessage());
            }
        });
        requestQueue.add(request);
    }
    public void initialise() {
        agreedtotnc = findViewById(R.id.agreetotnc);
        confirmslotbtn = findViewById(R.id.confirmslotbtn);
        name = findViewById(R.id.name);
        currentaddress = findViewById(R.id.address);
        peraddress = findViewById(R.id.address2);
        city =  findViewById(R.id.city);
        phoneno =  findViewById(R.id.phoneno);
        pincode = findViewById(R.id.pincode);
        dateofbooking = findViewById(R.id.dateofbooking);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities_list);
        city.setAdapter(adapter);
    }
    public boolean checkfields() {

        if(dateofbooking.getText().toString().isEmpty()){
            dateofbooking.setError("date can't be empty");
            dateofbooking.requestFocus();
            return false;
        }

        if(!agreedtotnc.isChecked()){
            agreedtotnc.setError("please agree to move forward");
            agreedtotnc.requestFocus();
            return false;
        }

        if(name.getText().toString().isEmpty()){
            name.setError("name can't be empty");
            name.requestFocus();
            return false;
        }
        if(currentaddress.getText().toString().isEmpty()) {
            currentaddress.setError("address can't be empty");
            currentaddress.requestFocus();
            return false;
        }
        if(peraddress.getText().toString().isEmpty()){
            peraddress.setError("address can't be empty");
            peraddress.requestFocus();
            return false;
        }
        if(city.getText().toString().isEmpty()){
            city.setError("city can't be empty");
            city.requestFocus();
            return false;
        }
        if(phoneno.getText().toString().isEmpty()){
            phoneno.setError("phoneno can't be empty");
            phoneno.requestFocus();
            return false;
        }
        if(pincode.getText().toString().isEmpty()) {
            pincode.setError("PINcode can't be empty");
            pincode.requestFocus();
            return false;
        }
        if(name.length()<=2){
            name.setError("name too small");
            name.requestFocus();
            return false;
        }
        if(currentaddress.length()<=5) {
            currentaddress.setError("address too small");
            currentaddress.requestFocus();
            return false;
        }
        if(peraddress.length()<=5) {
            peraddress.setError("address too small");
            peraddress.requestFocus();
            return false;
        }
        if(phoneno.length()!=10){
            phoneno.setError("Invalid phoneno");
            phoneno.requestFocus();
            return false;
        }
        if(pincode.length()!=6){
            pincode.setError("Invalid PINcode");
            pincode.requestFocus();
            return false;
        }

        return true;
    }

}