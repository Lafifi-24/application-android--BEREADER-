package com.example.readers0002.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.AcceilActivity;
import com.example.readers0002.R;

import org.json.JSONException;
import org.json.JSONObject;

public class verify_email_phone extends AppCompatActivity {

    private EditText codeemail;
    private Button validy;
    private String username;
    private String sendUrl="https://192.168.137.181/readers/C/validation.php?";
    private int success;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_phone);
        codeemail=findViewById(R.id.codeemail);
        username=getIntent().getStringExtra("username");
        validy=findViewById(R.id.valideemail);
        validy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(verify_email_phone.this, "a successful operation", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otherActivity =new Intent(getApplicationContext(), Login.class);
                        startActivity(otherActivity);
                        finish();
                    }
                }, 2000);

            }
        });


    }
    private  void sendData(){
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url=sendUrl+"username="+username+"&key="+codeemail.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    success = response.getInt("success");






                } catch (JSONException e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                success=5;
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);

    }
}