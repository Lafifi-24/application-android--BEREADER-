package com.example.readers0002.books;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.readers0002.R;
import com.example.readers0002.recommende;
import com.example.readers0002.search;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_books extends RecyclerView.Adapter<adapter_books.ViewHolder> {
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
        private TextView rating,type,language;
        private ImageView icon;
        private ImageView star_rating0;



        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.

            title = (TextView) v.findViewById(R.id.titlebook);
            subtitle=(TextView)v.findViewById(R.id.subtitlebook);
            icon=(ImageView) v.findViewById(R.id.iconbook);
            star_rating0=(ImageView) v.findViewById(R.id.star_rating1);
            subtitle=(TextView)v.findViewById(R.id.subtitlebook);


            author=(TextView) v.findViewById(R.id.author);
            rating=(TextView) v.findViewById(R.id.rating);

        }


    }

    public adapter_books(Context context, List<book> itemm) {
        items = itemm;
        this.context=context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.adapter_book, viewGroup,false);

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

        //viewHolder.getTextView().setText((CharSequence) items.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click listener method we are calling a new activity
                // and passing all the data of that item in next intent.
                if(search.b==0){
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






                    context.startActivity(i);
                }else{


                    Intent i = new Intent(context, recommende.class);
                    i.putExtra("id", m.getId());
                    i.putExtra("title", m.getTitle());
                    i.putExtra("icon", m.getThumbnailUrl());
                    context.startActivity(i);

                }

            }
        });
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }
}
