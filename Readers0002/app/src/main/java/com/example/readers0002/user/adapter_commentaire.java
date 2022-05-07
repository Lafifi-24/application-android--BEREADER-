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
import com.example.readers0002.AcceilActivity;
import com.example.readers0002.R;
import com.example.readers0002.books.adapter_books;
import com.example.readers0002.books.book;
import com.example.readers0002.books.detail_book;
import com.example.readers0002.recommende;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;


public class adapter_commentaire extends RecyclerView.Adapter<adapter_commentaire.ViewHolder>{
    private List<commentaire> items;
    private Context context;
    private user User;
    public adapter_commentaire(Context context, List<commentaire> itemm) {
        User=new user();
        items = itemm;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.adapter_commentaire, parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        commentaire v=items.get(position);
        holder.comment.setText(v.getCommentairetext());
        holder.date.setText(v.getDate());
        holder.username.setText(v.getUsername());
        commentaire a=v;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(a.getUsername().equals(User.getUserName())){
                    AlertDialog.Builder popup=new AlertDialog.Builder(context);
                    popup.setTitle("DELETE THIS COMMENT");
                    popup.setMessage("are you sure?");
                    popup.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url="http://192.168.137.181/readers/C/suppcomment.php?idcomment="+a.getIdcommentaire();
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
                            removeItem(position);

                            }

                    });
                    popup.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    popup.show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView comment,date,username;

        public ViewHolder(View v) {
            super(v);
            comment=v.findViewById(R.id.commentaire);
            date=v.findViewById(R.id.date_commentaire);
            username=v.findViewById(R.id.username);

            // Define click listener for the ViewHolder's View.



        }


    }
    private void removeItem(int position) {

        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

}
