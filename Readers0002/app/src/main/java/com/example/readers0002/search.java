package com.example.readers0002;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.readers0002.books.adapter_books;
import com.example.readers0002.books.book;
import com.example.readers0002.user.singin;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class search extends AppCompatActivity {
    private List<book> items;
    public static int b;
    private ProgressBar progressBar;
    private RequestQueue mRequestQueue,requestQueue;

    private String author,category,publisher;
    private ProgressDialog progress;
    private AlertDialog dialog;
    private AlertDialog.Builder dialog_builder;
    private TextView author_search,categry_search,publisher_search;
    private RecyclerView recyclerView;
    private ImageView search_Button,back_Button,scan_code,more_datail;
    private EditText search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        author_search=findViewById(R.id.author_search);
        categry_search=findViewById(R.id.category_search);
        publisher_search=findViewById((R.id.publisher_search));
        more_datail=findViewById(R.id.more_datail);
        recyclerView=(RecyclerView) findViewById(R.id.list_of_books);
        search_Button=findViewById(R.id.search_button);
        back_Button=findViewById(R.id.back_button000);
        search_text=findViewById(R.id.search_text);
        scan_code=findViewById(R.id.scan_code);
        progressBar=findViewById(R.id.idLoadingPB);
        progressBar.setVisibility(View.VISIBLE);
        progress=new ProgressDialog(this);
        dialog_builder=new AlertDialog.Builder(this);
        more_datail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup=getLayoutInflater().inflate(R.layout.search_popu,null);
                EditText search_author,search_category,search_publisher;
                Button validy;
                validy=popup.findViewById(R.id.validy);
                search_author=popup.findViewById(R.id.author);
                search_category=popup.findViewById(R.id.category);
                search_publisher=popup.findViewById(R.id.publisher);
                dialog_builder.setView(popup);
                dialog=dialog_builder.create();
                dialog.show();
                validy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean test_author=true,test_publisher=true,test_category=true;
                        expression f;
                        if(search_author.getText().length()!=0){
                            author=search_author.getText().toString();
                            f=new expression(author);
                            test_author=f.test_text();
                        }
                        else author=null;
                        if(search_publisher.getText().length()!=0){
                            publisher=search_publisher.getText().toString();
                            f=new expression(publisher);
                            test_publisher=f.test_text();
                        }
                        else publisher=null;
                        if(search_category.getText().length()!=0){
                            category=search_category.getText().toString();
                            f=new expression(category);
                            test_category=f.test_text();
                        }
                        else category=null;
                        if(test_author && test_category && test_publisher){
                            dialog.dismiss();
                            more_detail(author,category,publisher);
                        }else{
                            Toast.makeText(search.this,"no",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent otherActivity =new Intent(getApplicationContext(),AcceilActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
        getBooksInfo(search.this,"a");
        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_text.getText().toString().isEmpty()&& author==null && category==null && publisher==null) {
                    search_text.setError("Please enter search query");
                    return;
                }
                expression f=new expression(search_text.getText().toString());

                if(f.type().equals("number")&&(f.length()!=10||f.length()!=13)){
                    Toast.makeText(search.this,"it is not isbn",Toast.LENGTH_SHORT).show();
                    return;
                }

                progress.show();
                progress.setContentView(R.layout.progress_pupop);
                progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressBar.setVisibility(View.VISIBLE);
                getBooksInfo(search.this,search_text.getText().toString()+"");

            }
        });
        scan_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator =new IntentIntegrator(
                        search.this
                );
                intentIntegrator.setPrompt("ISBN");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });


    }
    private void more_detail(String author,String category,String publisher){
        if(author!=null){
            this.author_search.setText("author  :  "+author);
            this.author_search.setVisibility(View.VISIBLE);
        }else{
            this.author_search.setVisibility(View.GONE);
        }
        if(category!=null){
            this.categry_search.setText("category  :  "+category);
            this.categry_search.setVisibility(View.VISIBLE);
        }else{
            this.categry_search.setVisibility(View.GONE);
        }
        if(publisher!=null){
            this.publisher_search.setText("publisher  :  "+publisher);
            this.publisher_search.setVisibility(View.VISIBLE);
        }else{
            this.publisher_search.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null){

            search_text.setText(intentResult.getContents());

        }else{
            Toast.makeText(getApplicationContext(),"nonono",Toast.LENGTH_SHORT).show();
        }
    }
    private void sendBooksInfo(book m){
        requestQueue=Volley.newRequestQueue(getApplicationContext());
        String sendUrl="http://192.168.137.181/readers/C/AddBook.php";
        StringRequest request=new StringRequest(Request.Method.POST, sendUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            public Map<String,String> getParams(){
                Map<String, String> params=new HashMap<String, String>();
                params.put("traget","0");
                params.put("idgoogle",m.getIdgoogle());
                params.put("title",m.getTitle());
                params.put("subtitle",m.getSubtitle());
                params.put("author",m.getAuthors());
                params.put("nbrpages",m.getPageCount());
                params.put("description",m.getDescription());
                params.put("isbn13",m.getIsbn());
                params.put("category",m.getCategories());
                params.put("pubname",m.getPublisher());
                params.put("pubdate",m.getPublishedDate());
                params.put("language",m.getLanguage());
                params.put("imagebook",m.getThumbnailUrl());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);
    }

    private void getBooksInfo(Context context,String but) {
        items=new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(search.this);
        String lien="https://www.googleapis.com/books/v1/volumes?q=";
        List idbook=new ArrayList();
        RequestQueue queue = Volley.newRequestQueue(search.this);
        String url = lien + urlapi(but) +"&maxResults=40&printType=books";
        String url1="http://192.168.137.181/readers/C/sendBooks.php?"+urldb(but);

        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progress.dismiss();
                try {

                    JSONArray itemsArray = response.getJSONArray("books");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);

                        int id;
                        id=itemsObj.getInt("id");
                        String idgoogle;
                        idgoogle=itemsObj.getString("idgoogle");
                        idbook.add(idgoogle);
                        //title
                        String title = itemsObj.getString("title");

                        //subtitle
                        String subtitle;
                        if (itemsObj.has("subtitle"))
                            subtitle = itemsObj.getString("subtitle");
                        else subtitle = "";
                        //authors;
                        String authors;
                            authors=itemsObj.optString("authors");



                        //rating
                        double rating=itemsObj.optDouble("rating");

                        //page cont
                        int pageCount=0;
                        if(itemsObj.has("pageCount")) {
                            pageCount=itemsObj.getInt("pageCount");

                        }
                        //description
                        String description="--";
                        if (itemsObj.has("description"))
                            description = itemsObj.getString("description");

                        //isbn
                        String Isbn="--";
                        Isbn=itemsObj.getString("isbn13");


                        //categories
                        String categories="--";
                        categories=itemsObj.getString("category");

                        //publisher
                        String publishedDate="--";
                        if (itemsObj.has("publishedDate"))
                            publishedDate = itemsObj.getString("publishedDate");


                        String publisher="--";
                        if (itemsObj.has("publisher"))
                            publisher = itemsObj.getString("publisher");


                        //type book or magazing


                        //language
                        String language="--";
                        if (itemsObj.has("language")){
                            language=language(itemsObj.getString("language"));
                        }


                        //icon

                        String thumbnailUrl = "null";
                        thumbnailUrl=itemsObj.getString("pathimage");

                        book BOOK=new book(id,idgoogle,rating,title,  subtitle,authors, description, Isbn, categories, publishedDate,publisher,
                                thumbnailUrl,pageCount,language);
                        //sendBooksInfo(BOOK);
                        items.add(BOOK);

                        adapter_books adapter = new adapter_books(context, items);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        recyclerView.setAdapter(adapter);

                    }
                    JsonObjectRequest booksObjrequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // inside on response method we are extracting all our json data.
                            try {





                                JSONArray itemsArray = response.getJSONArray("items");
                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject itemsObj = itemsArray.getJSONObject(i);
                                    JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");

                                    String idgoogle;
                                    idgoogle=itemsObj.getString("id");
                                    if(idbook.contains(idgoogle))continue;
                                   //title
                                    String title = volumeObj.getString("title");

                                    //subtitle
                                    String subtitle;
                                    if (volumeObj.has("subtitle"))
                                        subtitle = volumeObj.getString("subtitle");
                                    else subtitle = "";
                                    //authors;
                                    String authors;



                                    if (volumeObj.has("authors")){
                                        JSONArray authorsArray = volumeObj.getJSONArray("authors");

                                        authors=authorsArray.optString(0);
                                        for (int j = 1; j < authorsArray.length(); j++) {
                                            authors=authors+","+authorsArray.optString(j);
                                        }
                                    }
                                    else authors = "--";



                                    //page cont
                                    int pageCount=0;
                                    if(volumeObj.has("pageCount")) {
                                        pageCount=volumeObj.getInt("pageCount");

                                    }
                                    //description
                                    String description="--";
                                    if (volumeObj.has("description"))
                                        description = volumeObj.getString("description");

                                    //isbn
                                    String Isbn="--";
                                    String type;

                                    if(volumeObj.has("industryIdentifiers")){
                                        JSONArray industryIdentifiers = volumeObj.optJSONArray("industryIdentifiers");
                                        for(int j=0;j<industryIdentifiers.length();j++){
                                            type=industryIdentifiers.getJSONObject(j).getString("identifier");
                                            if(type.length()==13) Isbn=industryIdentifiers.getJSONObject(j).getString("identifier");
                                        }
                                    }


                                    //categories
                                    String categories="--";
                                    if (volumeObj.has("categories")){
                                        JSONArray categoriesArray = volumeObj.getJSONArray("categories");

                                        categories=categoriesArray.optString(0);
                                        for (int j = 1; j < categoriesArray.length(); j++) {
                                            categories=authors+","+categoriesArray.optString(j);
                                        }
                                    }

                                    //publisher
                                    String publishedDate="--";
                                    if (volumeObj.has("publishedDate"))
                                        publishedDate = volumeObj.getString("publishedDate");


                                    String publisher="--";
                                    if (volumeObj.has("publisher"))
                                        publisher = volumeObj.getString("publisher");


                                    //type book or magazing
                                    String printType=volumeObj.getString("printType");
                                    if(!printType.equals("BOOK"))continue;

                                    //language
                                    String language="--";
                                    if (volumeObj.has("language")){
                                        language=language(volumeObj.getString("language"));
                                    }


                                    //icon

                                    String thumbnailUrl = "null";
                                    JSONObject thumbnailUrlObject = volumeObj.optJSONObject("imageLinks");
                                    if (thumbnailUrlObject != null && thumbnailUrlObject.has("thumbnail")) {
                                        thumbnailUrl = thumbnailUrlObject.getString("thumbnail");
                                    }

                                    book BOOK=new book(idgoogle,title,  subtitle,authors, description, Isbn, categories, publishedDate,publisher,
                                            thumbnailUrl,pageCount,language);
                                    sendBooksInfo(BOOK);
                                    items.add(BOOK);

                                    adapter_books adapter = new adapter_books(context, items);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(search.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(search.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    mRequestQueue.add(booksObjrequest1);
                }
                catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getBooksInfoapi(context,but);
            }
        });

        queue.add(booksObjrequest);

    }
    private void getBooksInfoapi(Context context,String but) {

        // creating a new array list.

        items=new ArrayList<>();
        // below line is use to initialize
        // the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(search.this);

        // below line is use to clear cache this
        // will be use when our data is being updated.
        mRequestQueue.getCache().clear();

        // below is the url for getting data from API in json format.

        String lien="https://www.googleapis.com/books/v1/volumes?q=";




        // below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(search.this);


        String url = lien + urlapi(but) +"&maxResults=40&printType=books";
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    progress.dismiss();
                // inside on response method we are extracting all our json data.
                try {





                    JSONArray itemsArray = response.getJSONArray("items");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");

                        String idgoogle;
                        idgoogle=itemsObj.getString("id");
                        //title
                        String title = volumeObj.getString("title");

                        //subtitle
                        String subtitle;
                        if (volumeObj.has("subtitle"))
                            subtitle = volumeObj.getString("subtitle");
                        else subtitle = "";
                        //authors;
                        String authors;



                        if (volumeObj.has("authors")){
                            JSONArray authorsArray = volumeObj.getJSONArray("authors");

                                authors=authorsArray.optString(0);
                                for (int j = 1; j < authorsArray.length(); j++) {
                                    authors=authors+","+authorsArray.optString(j);
                                }
                        }
                        else authors = "--";


                        //rating
                        double averageRating;
                        int ratingsCount;
                        if (volumeObj.has("averageRating")) {
                            averageRating = volumeObj.getDouble("averageRating");
                            ratingsCount = volumeObj.getInt("ratingsCount");
                        } else {
                            averageRating = 0;
                            ratingsCount = 0;
                        }
                        //page cont
                        int pageCount=0;
                        if(volumeObj.has("pageCount")) {
                            pageCount=volumeObj.getInt("pageCount");

                        }
                        //description
                        String description="--";
                        if (volumeObj.has("description"))
                            description = volumeObj.getString("description");

                        //isbn
                        String Isbn="--";
                        String type;

                        if(volumeObj.has("industryIdentifiers")){
                            JSONArray industryIdentifiers = volumeObj.optJSONArray("industryIdentifiers");
                            for(int j=0;j<industryIdentifiers.length();j++){
                                type=industryIdentifiers.getJSONObject(j).getString("identifier");
                                if(type.length()==13) Isbn=industryIdentifiers.getJSONObject(j).getString("identifier");
                            }
                        }


                        //categories
                        String categories="--";
                        if (volumeObj.has("categories")){
                            JSONArray categoriesArray = volumeObj.getJSONArray("categories");

                            categories=categoriesArray.optString(0);
                            for (int j = 1; j < categoriesArray.length(); j++) {
                                categories=authors+","+categoriesArray.optString(j);
                            }
                        }

                        //publisher
                        String publishedDate="--";
                        if (volumeObj.has("publishedDate"))
                            publishedDate = volumeObj.getString("publishedDate");


                        String publisher="--";
                        if (volumeObj.has("publisher"))
                            publisher = volumeObj.getString("publisher");


                        //type book or magazing
                        String printType=volumeObj.getString("printType");
                        if(!printType.equals("BOOK"))continue;

                        //language
                        String language="--";
                        if (volumeObj.has("language")){
                            language=language(volumeObj.getString("language"));
                        }


                        //icon

                        String thumbnailUrl = "null";
                        JSONObject thumbnailUrlObject = volumeObj.optJSONObject("imageLinks");
                        if (thumbnailUrlObject != null && thumbnailUrlObject.has("thumbnail")) {
                            thumbnailUrl = thumbnailUrlObject.getString("thumbnail");
                        }

                        book BOOK=new book(idgoogle,title,  subtitle,authors, description, Isbn, categories, publishedDate,publisher,
                                thumbnailUrl,pageCount,language);
                       //sendBooksInfo(BOOK);
                        items.add(BOOK);

                        adapter_books adapter = new adapter_books(context, items);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        recyclerView.setAdapter(adapter);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(search.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(search.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(booksObjrequest);
    }
    private String language(String language){

        switch (language){
            case "en":language="English";break;
            case "fr":language="French" ;break;
            case "ar":language="arab";break;
        }
        return language;
    }
    private String urldb(String but){
        expression f;
        String author="",category="",publisher="",title="";
        f=new expression(but);
        if(f.type().equals("number")&&f.length()==10){
            return "isbn=978"+but;
        }
        if(f.type().equals("number")){
            return "isbn="+but;
        }
        if(f.type().equals("text"))title="title="+but;
        if(this.category!=null)category="category="+this.category;
        if(this.publisher!=null)publisher="publisher="+this.publisher;
        if(this.author!=null)author="author="+this.author;
        return title+"&"+author+"&"+category+"&"+publisher;
    }
    private String urlapi(String but){
        expression f;
        String author="",category="",publisher="",title="";
        f=new expression(but);


        if(f.type().equals("number")&&f.length()==10){
            return "isbn:978"+but;
        }
        if(f.type().equals("number")){
            return "isbn:"+but;
        }


        if(f.type().equals("text"))title="intitle:"+but;
        if(this.category!=null)category="subject:"+this.category;
        if(this.publisher!=null)publisher="inpublisher:"+this.publisher;
        if(this.author!=null)author="inauthor:"+this.author;
        return title+"+"+author+"+"+category+"+"+publisher;
    }


}