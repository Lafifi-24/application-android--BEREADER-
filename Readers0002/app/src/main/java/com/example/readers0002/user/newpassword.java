package com.example.readers0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readers0002.user.Login;
import com.example.readers0002.user.singin;
import com.example.readers0002.user.verify_email_phone;

public class newpassword extends AppCompatActivity {
    private EditText password,confirm;
    private Button valide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        password=findViewById(R.id.ttxtpassword);
        confirm=findViewById(R.id.confirmmPassword);
        valide=findViewById(R.id.validee1);
        valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(password.getText().toString().equals(confirm.getText().toString()))){
                    Toast.makeText(newpassword.this, "verify password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(newpassword.this, "a successful operation", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otherActivity =new Intent(getApplicationContext(), Login.class);
                        startActivity(otherActivity);
                        finish();
                    }
                }, 1000);
            }
        });



    }
}