package com.example.readers0002.fragment_menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.readers0002.search;
import com.example.readers0002.user.Login;
import com.example.readers0002.user.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    private RequestQueue mRequestQueue;
    private List<book> items;
    private RecyclerView recyclerView;
    private Context context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Random rnd = new Random();
        char c = (char) ('a' + rnd.nextInt(26));
        String s=c+"";
        View view=getView();


        getBooksInfo(context,s);




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=v.findViewById(R.id.list_of_books);
        context= getActivity();
        // Inflate the layout for this fragment
        return v;
    }
    private void getBooksInfo(Context context, String but) {
        items=new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.context);


        String url1="http://192.168.137.181/readers/C/sendBooks.php?title="+but;
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray itemsArray = response.getJSONArray("books");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);

                        int id;
                        id = itemsObj.getInt("id");
                        String idgoogle;
                        idgoogle = itemsObj.getString("idgoogle");

                        //title
                        String title = itemsObj.getString("title");

                        //subtitle
                        String subtitle;
                        if (itemsObj.has("subtitle"))
                            subtitle = itemsObj.getString("subtitle");
                        else subtitle = "";
                        //authors;
                        String authors;
                        authors = itemsObj.optString("authors");


                        //rating
                        double rating = itemsObj.optDouble("rating");

                        //page cont
                        int pageCount = 0;
                        if (itemsObj.has("pageCount")) {
                            pageCount = itemsObj.getInt("pageCount");

                        }
                        //description
                        String description = "--";
                        if (itemsObj.has("description"))
                            description = itemsObj.getString("description");

                        //isbn
                        String Isbn = "--";
                        Isbn = itemsObj.getString("isbn13");


                        //categories
                        String categories = "--";
                        categories = itemsObj.getString("category");

                        //publisher
                        String publishedDate = "--";
                        if (itemsObj.has("publishedDate"))
                            publishedDate = itemsObj.getString("publishedDate");


                        String publisher = "--";
                        if (itemsObj.has("publisher"))
                            publisher = itemsObj.getString("publisher");


                        //type book or magazing


                        //language
                        String language = "--";
                        if (itemsObj.has("language")) {
                            language = itemsObj.getString("language");
                        }


                        //icon

                        String thumbnailUrl = "null";
                        thumbnailUrl = itemsObj.getString("pathimage");

                        book BOOK = new book(id, idgoogle, rating, title, subtitle, authors, description, Isbn, categories, publishedDate, publisher,
                                thumbnailUrl, pageCount, language);
                        //sendBooksInfo(BOOK);
                        items.add(BOOK);

                        adapter_books adapter = new adapter_books(context, items);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        recyclerView.setAdapter(adapter);


                    }
                }
                catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(search.this, "Error found is db " + error, Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(booksObjrequest);

    }
}