package com.example.teletraderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

public class NewsActivity extends AppCompatActivity {
    NewsViewModel viewModel;
    NewsAdapter adapter;
    RecyclerView newsRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("News");
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
            viewModel.getNews().observe(this, newsList -> {
                adapter = new NewsAdapter(newsList);
                newsRecyclerView = findViewById(R.id.newsRecyclerView);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                newsRecyclerView.setLayoutManager(layoutManager);
                newsRecyclerView.setAdapter(adapter);
            });

        }
    }

}
