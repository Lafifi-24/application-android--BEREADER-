package com.example.readers0002.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.R;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class singin extends AppCompatActivity {

    private EditText password,Nphone,email,username,confirm;
    private Button save;
    private String sendUrl="http://192.168.137.181/readers/C/signup.php",nphone;
    private RequestQueue requestQueue;
    private CountryCodePicker ccp;
    private boolean isvalidenumber;
    private ImageView truefalse;

    private int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);

        password=findViewById(R.id.txtpassword);
        confirm=findViewById(R.id.confirmPassword);
        Nphone=findViewById(R.id.txtphone);
        email=findViewById(R.id.txtemail);
        save=findViewById(R.id.valider);
        username=findViewById(R.id.txtusername);




        ValidyPhoneNumber();
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean patternemail;
                nphone=ccp.getSelectedCountryCodeWithPlus().toString()+Nphone.getText().toString();
                patternemail= Pattern.matches("[a-zA-Z]+[a-zA-Z0-9_.]*[@][a-zA-Z]+[.][a-zA-Z]+", email.getText().toString());
                if(!patternemail){
                    Toast.makeText(singin.this, "wrong writing email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(password.getText().toString().equals(confirm.getText().toString()))){
                    Toast.makeText(singin.this, " password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                signup();


            }
        });




    }
    private  void   ValidyPhoneNumber(){
        ccp=findViewById(R.id.ccp);

        Nphone=findViewById(R.id.txtphone);
        ccp.registerCarrierNumberEditText(Nphone);

        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {


            @Override
            public void onValidityChanged(boolean isValidNumber) {

                isvalidenumber=isValidNumber;
            }
        });

    }

    private  void signup(){
        String country=ccp.getSelectedCountryName();
        String Email=email.getText().toString();
        String Username=username.getText().toString();
        String Password=password.getText().toString();
        String url=sendUrl+"?username="+Username+"&email="+Email+"&Nphone="+nphone+"&country="+country+"&password="+password;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    success = response.getInt("success");
                    /*if(success==0) {
                        Toast.makeText(singin.this, "please change your username", Toast.LENGTH_SHORT).show();

                    }else{
                        if(success==1){
                            Toast.makeText(singin.this, "please change your email", Toast.LENGTH_SHORT).show();

                        }else{*/
                            Intent otherActivity =new Intent(getApplicationContext(), verify_email_phone.class);
                            otherActivity.putExtra("username",username.getText().toString());
                            startActivity(otherActivity);
                            finish();
                        //}
                    //}


                } catch (JSONException e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);

    }
}