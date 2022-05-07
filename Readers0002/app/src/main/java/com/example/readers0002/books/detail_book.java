package com.example.readers0002.books;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.R;
import com.example.readers0002.search;
import com.example.readers0002.user.adapter_commentaire;
import com.example.readers0002.user.commentaire;
import com.example.readers0002.user.user;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class detail_book extends AppCompatActivity {
    private TextView  title,subtitle,authors,description,Isbn,categories,publishedDate,publisher,textlist,textrating,ratingsCount_google,language,RatingsCount,Rating, pageCount, averageRatinggoogle;
    private ImageView icon,retor,newcomment,cancelcomment,addcomment,add_myrating,add_mylist;
    private LinearLayout barcomment;
    private EditText tapcomment;
    private List<commentaire> list;
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerView;
    private String idbook,idgoogle;
    private AlertDialog dialog;
    private AlertDialog.Builder dialog_builder;
    private user User;
    private int inmylist,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User=new user();
        setContentView(R.layout.activity_detail_book);
        add_myrating=findViewById(R.id.add_myrating);
        tapcomment=findViewById(R.id.tapcomment);
        barcomment=findViewById(R.id.barcomment);
        newcomment=findViewById(R.id.newcomment);
        cancelcomment=findViewById(R.id.cancelcomment);
        addcomment=findViewById(R.id.addcomment);
        title=findViewById(R.id.titlebook);
        authors=findViewById(R.id.author);
        icon=findViewById(R.id.iconbook);
        recyclerView=findViewById(R.id.list_of_commentaire);
        subtitle=findViewById(R.id.subtitlebook);
        retor=findViewById(R.id.retor);
        description=findViewById(R.id.description);
        Isbn=findViewById(R.id.ISBN);
        categories=findViewById(R.id.categories);
        publishedDate=findViewById(R.id.publisherDate);
        publisher=findViewById(R.id.publisher);
        textrating=findViewById(R.id.text_rating);
        add_mylist=findViewById(R.id.add_mylist);
        language=findViewById(R.id.language);
        Rating=findViewById(R.id.rating);
        RatingsCount=findViewById(R.id.ratingCount);
        pageCount=findViewById(R.id.pages);
        dialog_builder=new AlertDialog.Builder(this);
        averageRatinggoogle=findViewById(R.id.rating_google);
        ratingsCount_google=findViewById(R.id.ratingCount_google);
        textlist=findViewById(R.id.type_of_list);


        add_mylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getId()==-1){
                    Toast.makeText(detail_book.this,"you are not connected",Toast.LENGTH_SHORT).show();
                }else {
                    if(inmylist==1){
                        delete(parseInt(idbook));
                        add_mylist.setImageResource(R.drawable.ic_baseline_add_24);
                        textlist.setText("my list");
                        getinfo(detail_book.this);
                    }else{
                        add_inMylist();
                        add_mylist.setImageResource(R.drawable.ic_baseline_list_24);
                        textlist.setText("in my list");
                        getinfo(detail_book.this);
                    }

                }



            }
        });

        retor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_book.this.finish();
            }
        });
        add_myrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getId()==-1){
                    Toast.makeText(detail_book.this,"you are not connected",Toast.LENGTH_SHORT).show();
                }else{
                    View popup=getLayoutInflater().inflate(R.layout.ratingbar,null);
                    dialog_builder.setView(popup);
                    dialog=dialog_builder.create();
                    dialog.show();
                    RatingBar rating;
                    rating=popup.findViewById(R.id.barrating);
                    rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            textrating.setText(rating+"");
                            send_rating(rating);
                            add_myrating.setImageResource(R.drawable.ic_baseline_star_rate_24);
                            dialog.dismiss();
                            getinfo(detail_book.this);
                        }
                    });
                }

            }
        });
        newcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(User.getId()==-1){
                        Toast.makeText(detail_book.this,"you are not connected",Toast.LENGTH_SHORT).show();
                    }else{
                        barcomment.setVisibility(View.VISIBLE);
                    }

            }
        });
        cancelcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcomment.setVisibility(View.GONE);
                tapcomment.setText("");
            }
        });
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_comment(tapcomment.getText().toString());

                tapcomment.setText("");
                getinfo(detail_book.this);
            }
        });


        idbook=getIntent().getStringExtra("id");
        idgoogle=getIntent().getStringExtra("idgoogle");
        title.setText(getIntent().getStringExtra("title"));
        authors.setText(getIntent().getStringExtra("authors"));

        subtitle.setText(getIntent().getStringExtra("subtitle"));

        description.setText(getIntent().getStringExtra("description"));
        Isbn.setText(getIntent().getStringExtra("ISBN"));
        categories.setText(getIntent().getStringExtra("categories"));
        publishedDate.setText(getIntent().getStringExtra("publishedDate"));
        publisher.setText(getIntent().getStringExtra("publisher"));

        language.setText(getIntent().getStringExtra("language"));

        pageCount.setText("pages : "+getIntent().getStringExtra("pages"));
       // averageRating.setText(getIntent().getStringExtra("rating"));
        //ratingsCount.setText(getIntent().getStringExtra("cont_rating"));


        if(getIntent().getStringExtra("icon")!="null"){

            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    exception.printStackTrace();
                }
            });
            builder.build().load(getIntent().getStringExtra("icon")).into(icon);
        }
        getinfo(this);


    }
    private void send_rating(float rating){
        RequestQueue queue = Volley.newRequestQueue(detail_book.this);
        String url="http://192.168.137.181/readers/C/getrating.php?idbook="+idbook+"&rating="+rating+"&iduser="+User.getId();
        JsonObjectRequest commentObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(commentObjrequest);
    }
    private void add_inMylist(){
        RequestQueue queue = Volley.newRequestQueue(detail_book.this);
        String url="http://192.168.137.181/readers/C/add_inlist.php?idbook="+idbook+"&iduser="+User.getId();
        JsonObjectRequest commentObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // at last we are adding our json object
        // request in our request queue.

        queue.add(commentObjrequest);

    }
    private void delete(int idbook){
        user User=new user();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url="http://192.168.137.181/readers/C/deletefrommylist.php?idbook="+idbook+"&iduser="+User.getId();
        JsonObjectRequest commentObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(commentObjrequest);



    }
    private void send_comment(String text){


        RequestQueue queue = Volley.newRequestQueue(detail_book.this);
        String url="http://192.168.137.181/readers/C/getcomment.php?idbook="+idbook+"&comment="+text+"&iduser="+User.getId();
        JsonObjectRequest commentObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(commentObjrequest);
    }
    private void getrating_google(){
        mRequestQueue = Volley.newRequestQueue(detail_book.this);

        // below line is use to clear cache this
        // will be use when our data is being updated.
        mRequestQueue.getCache().clear();
        RequestQueue queue = Volley.newRequestQueue(detail_book.this);
        String url="https://www.googleapis.com/books/v1/volumes?q="+idgoogle;
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // inside on response method we are extracting all our json data.
                try {




                    JSONArray itemsArray = response.getJSONArray("items");

                    JSONObject itemsObj = itemsArray.getJSONObject(0);
                    JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                    double averageRating;
                    int ratingsCount;
                    if (volumeObj.has("averageRating")) {
                        averageRating = volumeObj.getDouble("averageRating");
                        ratingsCount = volumeObj.getInt("ratingsCount");
                    } else {
                        averageRating = 0;
                        ratingsCount = 0;
                    }
                    averageRatinggoogle.setText(averageRating+"");
                    ratingsCount_google.setText(ratingsCount+"");



                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(detail_book.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(detail_book.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(booksObjrequest);
    }
    public void getinfo(Context context) {
        getrating_google();

        list=new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(detail_book.this);


        mRequestQueue.getCache().clear();


        RequestQueue queue = Volley.newRequestQueue(detail_book.this);
        String url="http://192.168.137.181/readers/C/sendinfobook.php?idbook="+idbook+"&iduser="+User.getId();
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // inside on response method we are extracting all our json data.
                try {


                    int myrating=response.getInt("myrating");
                    if(myrating>0){
                        add_myrating.setImageResource(R.drawable.ic_baseline_star_rate_24);
                        textrating.setText(myrating+"");
                    }
                    inmylist=response.getInt("inmylist");
                    if(inmylist==1){
                        add_mylist.setImageResource(R.drawable.ic_baseline_list_24);
                        textlist.setText("in my list");
                    }else{
                        inmylist=0;
                    }
                    int ratingcount=response.getInt("ratingsCount");
                    RatingsCount.setText(""+ratingcount);
                    Double rating=response.getDouble("rating");
                    Rating.setText(""+rating);
                    if(response.has("comment")){
                        JSONArray comments = response.getJSONArray("comment");
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject comment=comments.getJSONObject(i);
                            int idcomment=comment.getInt("id");
                            int iduser=comment.getInt("iduser");
                            String username=comment.getString("username");
                            String textcomment=comment.getString("comment");
                            String datecomment=comment.getString("date");
                            list.add(new commentaire(iduser,idcomment,datecomment,username,textcomment));

                            adapter_commentaire adapter = new adapter_commentaire(context, list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(adapter);

                        }
                    }


                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(detail_book.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(detail_book.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(booksObjrequest);
    }

}