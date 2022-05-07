package com.example.readers0002;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.user.Login;
import com.example.readers0002.user.user;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readers0002.databinding.ActivityAcceilBinding;

import org.json.JSONObject;

public class AcceilActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAcceilBinding binding;
    private LinearLayout header;

    private ImageView imageicon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAcceilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarAcceil.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_mylist, R.id.nav_favorite)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_acceil);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView1.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.header);
        TextView username,email,deconnction;
        deconnction=header.findViewById(R.id.connected);
        username=headerview.findViewById(R.id.username);
        email=header.findViewById(R.id.email);

        user User;User=new user();
        if(User.getId()!=-1){
            email.setVisibility(View.VISIBLE);
            deconnction.setVisibility(View.VISIBLE);
            username.setText(User.getUserName());
            email.setText(User.getEmail());

        }
        deconnction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user User=new user();
                User.inisial();
                Intent otherActivity = new Intent(getApplicationContext(), AcceilActivity.class);
                startActivity(otherActivity);


            }
        });
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(user.getId()==-1) {
                    Intent otherActivity = new Intent(getApplicationContext(), Login.class);
                    startActivity(otherActivity);
                }


            }
            });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acceil, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            search.b=0;
            Intent otherActivity =new Intent(getApplicationContext(),search.class);
            startActivity(otherActivity);

        }
        if (id == R.id.recommand) {
            search.b=1;
            Intent otherActivity =new Intent(getApplicationContext(),recommende.class);
            startActivity(otherActivity);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_acceil);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}