package com.example.readers0002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.readers0002.books.detail_book;
import com.example.readers0002.fragment_menu.favorite;
import com.example.readers0002.user.user;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class recommende extends AppCompatActivity {
    private LinearLayout book1,book2;
    private static int c;
    private ImageView imagebook1,imagebook2;
    private TextView titlebook1,titlebook2;
    private EditText comment;
    private Button validy;
    private static String image1,image2,titre1,titre2,id1,id2,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommende);
        book1=findViewById(R.id.book1);
        book2=findViewById(R.id.book2);
        imagebook1=findViewById(R.id.imagebook1);
        imagebook2=findViewById(R.id.imagebook2);
        titlebook1=findViewById(R.id.titlebook1);
        titlebook2=findViewById(R.id.titlebook2);
        validy=findViewById(R.id.validrecommend);
        comment=findViewById(R.id.commentrecommand);

        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                Intent otherActivity =new Intent(getApplicationContext(),search.class);
                startActivity(otherActivity);
            }
        });
        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=2;
                Intent otherActivity =new Intent(getApplicationContext(),search.class);
                startActivity(otherActivity);
            }
        });
        if(c==1){
            id1=getIntent().getStringExtra("id");
            titre1=getIntent().getStringExtra("title");

            image1=getIntent().getStringExtra("icon");

        }
        if(c==2){
            id2=getIntent().getStringExtra("id");
            titre2=getIntent().getStringExtra("title");

            image2=getIntent().getStringExtra("icon");

        }
        if(titre1!=null)titlebook1.setText(titre1);
        if(image1!=null){

            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    exception.printStackTrace();
                }
            });
            builder.build().load(image1).into(imagebook1);
        }
        if(titre2!=null)titlebook2.setText(titre2);
        if(image2!=null){

            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    exception.printStackTrace();
                }
            });
            builder.build().load(image2).into(imagebook2);
        }
        validy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id1==null || id2==null){
                    Toast.makeText(recommende.this,"please ,choose two books",Toast.LENGTH_SHORT).show();
                }else{
                    if(id1==id2){
                        Toast.makeText(recommende.this,"please ,choose two different books",Toast.LENGTH_SHORT).show();
                    }else{

                        senddata();
                        Intent otherActivity =new Intent(getApplicationContext(), AcceilActivity.class);
                        startActivity(otherActivity);

                    }
                }
            }
        });
    }
    private void senddata(){
        RequestQueue queue = Volley.newRequestQueue(recommende.this);
        text=comment.getText().toString();
        String url="http://192.168.137.181/readers/C/getrecommand.php?idbook1="+id1+"&idbook2="+id2+"&iduser="+ user.getId()+"&text="+text;
        JsonObjectRequest commentObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(commentObjrequest);
    }
}