package com.example.readers0002.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.AcceilActivity;
import com.example.readers0002.R;
import com.example.readers0002.fragment_menu.mylist;
import com.example.readers0002.user.user;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.lang.Integer.parseInt;

public class adapterlist extends RecyclerView.Adapter<adapterlist.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<book> items;
    private Context context;
    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subtitle;
        private TextView author;
        private TextView rating;
        private ImageView icon;
        private ImageView delete;



        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.

            title = (TextView) v.findViewById(R.id.titlebook);
            subtitle=(TextView)v.findViewById(R.id.subtitlebook);
            icon=(ImageView) v.findViewById(R.id.iconbook);
            delete=(ImageView) v.findViewById(R.id.delete);
            subtitle=(TextView)v.findViewById(R.id.subtitlebook);


            author=(TextView) v.findViewById(R.id.author);
            rating=(TextView) v.findViewById(R.id.rating);

        }
    }

    public adapterlist(Context context, List<book> itemm) {
        items = itemm;
        this.context=context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.adapterlist, viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //Log.d(TAG, "Element " + position + " set.");
        book m=items.get(position);
        viewHolder.title.setText(m.getTitle());
        viewHolder.author.setText(m.getAuthors());
        viewHolder.subtitle.setText(m.getSubtitle());
        viewHolder.rating.setText(m.getRating()+"");

        if(m.getThumbnailUrl()!="null") {
            Picasso.with(context).load(m.getThumbnailUrl()).into(viewHolder.icon);

        }
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(parseInt(m.getId()));
                removeItem(position);
            }
        });
        //viewHolder.getTextView().setText((CharSequence) items.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click listener method we are calling a new activity
                // and passing all the data of that item in next intent.
                Intent i = new Intent(context, detail_book.class);
                i.putExtra("id", m.getId());
                i.putExtra("idgoogle",m.getIdgoogle());
                i.putExtra("title", m.getTitle());
                i.putExtra("authors", m.getAuthors());
                i.putExtra("subtitle", m.getSubtitle());

                i.putExtra("description", m.getDescription());
                i.putExtra("ISBN", m.getIsbn());
                i.putExtra("categories", m.getCategories());
                i.putExtra("publisher", m.getPublisher());
                i.putExtra("publishedDate", m.getPublishedDate());

                i.putExtra("language", m.getLanguage());
                i.putExtra("icon", m.getThumbnailUrl());
                i.putExtra("pages", m.getPageCount());





                // after passing that data we are
                // starting our new  intent.
                context.startActivity(i);
            }
        });
    }
    private void delete(int idbook){
        user User=new user();

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
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
    private void removeItem(int position) {

        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}