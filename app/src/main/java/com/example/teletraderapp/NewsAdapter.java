package com.example.teletraderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsAdapter.NewsViewHolder holder, int position) {
        News currentNews = newsList.get(position);
        holder.titleView.setText(currentNews.getHeadline().trim());
        String imageUrl = String.format("https://cdn.ttweb.net/News/images/%d.jpg?preset=w220_q40", currentNews.getImageId());
        ImageLoader imageLoader = CustomVolleyRequestQueue.getInstance(holder.imageView.getContext())
                .getImageLoader();
        imageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imageView,
                R.mipmap.ic_launcher, android.R.drawable
                        .ic_dialog_alert));
        holder.imageView.setImageUrl(imageUrl, imageLoader);


    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private NetworkImageView imageView;

        public NewsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.titleView = itemView.findViewById(R.id.titleView);
            this.imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
