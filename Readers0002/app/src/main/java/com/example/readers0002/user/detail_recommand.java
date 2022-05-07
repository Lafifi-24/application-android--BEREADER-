package com.example.readers0002.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.R;
import com.example.readers0002.books.adapter_books;
import com.example.readers0002.books.book;
import com.example.readers0002.books.recommend;
import com.example.readers0002.search;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class detail_recommand extends AppCompatActivity {
    private String idbook1,idbook2;
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerView;
    private ImageView imagebook1,imagebook2;
    private TextView titlebook1,titlebook2;
    private List<commentaire> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recommand);
        recyclerView=(RecyclerView) findViewById(R.id.listcomentaire);
        idbook1=getIntent().getStringExtra("idbook1");
        idbook2=getIntent().getStringExtra("idbook2");
        titlebook1=findViewById(R.id.titlebook1);
        titlebook2=findViewById(R.id.titlebook2);
        imagebook1=findViewById(R.id.imagebook1);
        imagebook2=findViewById(R.id.imagebook2);
        getBooksInfo(detail_recommand.this);




    }
    private void getBooksInfo(Context context) {

        // creating a new array list.

        list=new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(detail_recommand.this);

        String url ="http://192.168.137.181/readers/C/send_detail_recommend.php?idbook1="+idbook1+"&idbook2="+idbook2;
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responses) {



                try {
                    JSONObject response=responses.getJSONObject("r");
                    JSONObject book1=response.getJSONObject("book1");
                    JSONObject book2=response.getJSONObject("book2");
                    Picasso.with(context).load(book1.getString("pathimage")).into(imagebook1);
                    Picasso.with(context).load(book2.getString("pathimage")).into(imagebook2);
                    titlebook1.setText(book1.getString("title"));
                    titlebook2.setText(book2.getString("title"));

                    if(response.has("comment")){
                        JSONArray comments = response.getJSONArray("comment");
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject comment=comments.getJSONObject(i);
                            String username=comment.getString("username");
                            String textcomment=comment.getString("texte");
                            String datecomment=comment.getString("date");
                            list.add(new commentaire(1,1,datecomment,username,textcomment));
                            adapter_commentaire adapter = new adapter_commentaire(context, list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(adapter);

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(detail_recommand.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(detail_recommand.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // at last we are adding our json object
        // request in our request queue.
        mRequestQueue.add(booksObjrequest);
    }
}