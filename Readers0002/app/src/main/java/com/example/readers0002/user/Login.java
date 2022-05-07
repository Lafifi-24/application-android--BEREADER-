package com.example.readers0002.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.AcceilActivity;
import com.example.readers0002.R;
import com.example.readers0002.search;
import com.example.readers0002.youremail;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class Login extends AppCompatActivity {


    private EditText password,email;
    private static int RC_SIGN_IN= 100;
    private RequestQueue mRequestQueue;
    private Button send,singin;
    private LoginButton singin_facebook;
    private RequestQueue requestQueue;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private CallbackManager callbackManager;
    private AlertDialog dialog;
    private AlertDialog.Builder dialog_builder;
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        password=findViewById(R.id.txtPassword);
        email=findViewById(R.id.txtEmail);
        send=findViewById(R.id.login);
        singin=findViewById(R.id.singin);
        singin_facebook=(LoginButton) findViewById(R.id.sign_in_facebook);
        dialog_builder=new AlertDialog.Builder(this);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        callbackManager=CallbackManager.Factory.create();
        singin_facebook.registerCallback(callbackManager,new FacebookCallback<LoginResult>(){

            @Override
            public void onSuccess(LoginResult loginResult) {



                    Toast.makeText(Login.this,"success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this,"login cancels",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this,"error : " +error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        forget=findViewById(R.id.forgot);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity =new Intent(getApplicationContext(), youremail.class);
                startActivity(otherActivity);
            }
        });
        AccessTokenTracker t=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(oldAccessToken==null){
                    //Toast.makeText(Login.this,"Logout",Toast.LENGTH_SHORT).show();
                    loaduserprofile(currentAccessToken);
                }else{

                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signInButton = findViewById(R.id.sign_in_google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sende_mail_pass();

            }
        });
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity =new Intent(getApplicationContext(), singin.class);
                startActivity(otherActivity);
                finish();
            }
        });

    }

    private void loaduserprofile(AccessToken newAccessToken){

        GraphRequest request =GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                if(jsonObject!=null){
                    try{

                        String email=jsonObject.getString("email");
                        String id=jsonObject.getString("id");
                        login_face_google(email,id);
                        Intent otherActivity =new Intent(getApplicationContext(), AcceilActivity.class);
                        startActivity(otherActivity);
                        finish();



                    } catch (JSONException e) {
                        Toast.makeText((Login.this), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Bundle parame=new Bundle();
        parame.putString("fields","email,id");
        request.setParameters(parame);
        request.executeAsync();
    }
    private void login_face_google(String email,String id){
        mRequestQueue = Volley.newRequestQueue(Login.this);
        mRequestQueue.getCache().clear();
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        String url="http://192.168.137.181/readers/C/login_face_google.php?email="+email+"&id="+id;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt("success");
                    if(success==1){
                        user user=new user(response.getInt("id"),response.getString("username"),
                                response.getString("email"),response.getInt("signale"));

                    }else{
                        Toast.makeText(Login.this," ", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(Login.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
    private  void sende_mail_pass(){
        mRequestQueue = Volley.newRequestQueue(Login.this);
        mRequestQueue.getCache().clear();
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        String url="http://192.168.137.181/readers/C/loginUser.php?email="+email.getText().toString()+"&password="+password.getText().toString();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt("success");
                    if(success==1){
                        user user=new user(response.getInt("id"),response.getString("username"),
                                response.getString("email"),response.getInt("signale"));
                        Intent otherActivity =new Intent(getApplicationContext(), AcceilActivity.class);
                        startActivity(otherActivity);
                        finish();

                    }else{
                        Toast.makeText(Login.this," wrong password", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(Login.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    }





    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 12500);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
             callbackManager.onActivityResult(requestCode, resultCode, data);
                 Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                 handleSignInResult(task);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this );
            if (acct != null) {
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                login_face_google(personEmail,personId);
                Intent otherActivity =new Intent(getApplicationContext(), AcceilActivity.class);
                startActivity(otherActivity);
                finish();
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }



}