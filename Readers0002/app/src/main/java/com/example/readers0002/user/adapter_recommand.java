package com.example.readers0002.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.readers0002.R;
import com.example.readers0002.books.detail_book;
import com.example.readers0002.books.recommend;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;

import static java.lang.Integer.parseInt;

public class adapter_recommand extends RecyclerView.Adapter<adapter_recommand.ViewHolder>{
    private List<recommend> items;
    private Context context;
    private user User;
    public adapter_recommand(Context context, List<recommend> itemm) {
        User=new user();
        items = itemm;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.adapter_recommend, parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        recommend v=items.get(position);
        holder.comment.setText(v.getComment());
        holder.date.setText(v.getDate());
        holder.username.setText(v.getUsername());
        Picasso.with(context).load(v.getBook1().getThumbnailUrl()).into(holder.imagebook1);
        Picasso.with(context).load(v.getBook2().getThumbnailUrl()).into(holder.imagebook2);
        holder.titlebook1.setText(v.getBook1().getTitle());
        holder.titlebook2.setText(v.getBook2().getTitle());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vd) {
                Intent i = new Intent(context, detail_recommand.class);
                i.putExtra("idbook1",v.getBook1().getId());
                i.putExtra("idbook2",v.getBook2().getId());
                context.startActivity(i);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView comment,date,username,titlebook1,titlebook2,more;
        private ImageView imagebook1,imagebook2;

        public ViewHolder(View v) {
            super(v);
            comment=v.findViewById(R.id.commentairerecommende);
            date=v.findViewById(R.id.date_commentaire);
            username=v.findViewById(R.id.username);
            titlebook1=v.findViewById(R.id.titlebook1);
            titlebook2=v.findViewById(R.id.titlebook2);
            imagebook1=v.findViewById(R.id.imagebook1);
            imagebook2=v.findViewById(R.id.imagebook2);
            more=v.findViewById(R.id.more);

            // Define click listener for the ViewHolder's View.



        }


    }


}
