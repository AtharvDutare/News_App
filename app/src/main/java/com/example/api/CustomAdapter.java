package com.example.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Models.NewsHeadlines;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context ;
    private List<NewsHeadlines> headlines;
    SelectListener listener ;
    public CustomAdapter (Context context , List<NewsHeadlines>headlines,SelectListener listener){
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.headline_list_items,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        if(headlines.get(position).getUrlToImage() != null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.imgHeadline);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNewsClicked(headlines.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
