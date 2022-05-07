package com.example.readers0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.user.Login;
import com.example.readers0002.user.verify_email_phone;

import org.json.JSONException;
import org.json.JSONObject;

public class youremail extends AppCompatActivity {
    private TextView validy;
    private EditText email;
    private String sendUrl="http://192.168.137.181/readers/C/existemail.php?email=";
    private RequestQueue requestQueue;
    private int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youremail);
        email=findViewById(R.id.eemail);
        validy=findViewById(R.id.valideemail);
        validy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }
    private  void sendData(){
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url=sendUrl+email.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    success = response.getInt("success");
                    if(success==0){
                        Intent otherActivity =new Intent(getApplicationContext(), checkemail.class);
                        startActivity(otherActivity);
                    }else{
                        Toast.makeText(youremail.this, "email is not exist", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(youremail.this, "emmm"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(youremail.this, "e"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);

    }
}