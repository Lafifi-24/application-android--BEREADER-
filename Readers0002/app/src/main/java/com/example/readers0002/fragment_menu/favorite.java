package com.example.readers0002.fragment_menu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.readers0002.books.recommend;
import com.example.readers0002.user.adapter_recommand;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favorite extends Fragment {
    private RequestQueue mRequestQueue;
    private List<recommend> items;
    private RecyclerView recyclerView;
    private Context context;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public favorite() {
        // Required empty public constructor
    }
    public void onStart() {
        super.onStart();



        getrecommendInfo(context);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static favorite newInstance(String param1, String param2) {
        favorite fragment = new favorite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView=v.findViewById(R.id.list_of_recomand);
        context= getActivity();

        return v;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.acceil,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.recommand).setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }
   private void getrecommendInfo(Context context) {
        items=new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.context);


        String url1="http://192.168.137.181/readers/C/sendrecommend.php";
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responses) {

                try {

                  JSONArray r=responses.getJSONArray("r");

                      for(int i=0;i<r.length();i++) {

                            JSONObject response=r.getJSONObject(i);
                          String username = response.getString("username");
                          String date = response.getString("date");
                          String text = response.getString("text");
                          JSONObject itemsObj = response.getJSONObject("book1");

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

                          book book1 = new book(id, idgoogle, rating, title, subtitle, authors, description, Isbn, categories, publishedDate, publisher,
                                  thumbnailUrl, pageCount, language);

                          itemsObj = response.getJSONObject("book2");


                          id = itemsObj.getInt("id");

                          idgoogle = itemsObj.getString("idgoogle");

                          //title
                          title = itemsObj.getString("title");

                          //subtitle

                          if (itemsObj.has("subtitle"))
                              subtitle = itemsObj.getString("subtitle");
                          else subtitle = "";
                          //authors;

                          authors = itemsObj.optString("authors");


                          //rating
                          rating = itemsObj.optDouble("rating");

                          //page cont

                          if (itemsObj.has("pageCount")) {
                              pageCount = itemsObj.getInt("pageCount");

                          }
                          //description

                          if (itemsObj.has("description"))
                              description = itemsObj.getString("description");

                          //isbn

                          Isbn = itemsObj.getString("isbn13");


                          //categories

                          categories = itemsObj.getString("category");

                          //publisher

                          if (itemsObj.has("publishedDate"))
                              publishedDate = itemsObj.getString("publishedDate");


                          if (itemsObj.has("publisher"))
                              publisher = itemsObj.getString("publisher");


                          //type book or magazing


                          //language

                          if (itemsObj.has("language")) {
                              language = itemsObj.getString("language");
                          }


                          //icon


                          thumbnailUrl = itemsObj.getString("pathimage");

                          book book2 = new book(id, idgoogle, rating, title, subtitle, authors, description, Isbn, categories, publishedDate, publisher,
                                  thumbnailUrl, pageCount, language);


                        recommend recomend=new recommend(username,text,date,book1,book2);
                        items.add(recomend);
                        adapter_recommand adapter=new adapter_recommand(context,items);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        recyclerView.setAdapter(adapter);

                      }
                }
                catch (JSONException e) {
                    Toast.makeText(context, "Error foun"+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error found is db " + error, Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(booksObjrequest);

    }

}