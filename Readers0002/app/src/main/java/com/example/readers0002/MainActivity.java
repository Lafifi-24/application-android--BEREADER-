package com.example.readers0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.readers0002.user.user;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                user User=new user();
                User.inisial();
                Intent otherActivity =new Intent(getApplicationContext(),AcceilActivity.class);
                startActivity(otherActivity);

            }
        }, 2000);
    }

}