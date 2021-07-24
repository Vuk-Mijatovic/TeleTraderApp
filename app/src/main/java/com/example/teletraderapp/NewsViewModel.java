package com.example.teletraderapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository repository;

    private MutableLiveData<List<News>> newsList;


    public NewsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void loadNews() {
        repository = new NewsRepository();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<News> loadedNews = repository.fetchResponse();
                    newsList.postValue(loadedNews);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MutableLiveData<List<News>> getNews() {
        if (newsList == null) {
            newsList = new MutableLiveData<List<News>>();
            loadNews();
        }
        return newsList;
    }

}
