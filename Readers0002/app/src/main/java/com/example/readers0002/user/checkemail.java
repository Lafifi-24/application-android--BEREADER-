package com.example.readers0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class checkemail extends AppCompatActivity {
    private TextView validy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkemail);
        validy=findViewById(R.id.valideemaile);
        validy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity =new Intent(getApplicationContext(), newpassword.class);
                startActivity(otherActivity);
            }
        });
    }
}