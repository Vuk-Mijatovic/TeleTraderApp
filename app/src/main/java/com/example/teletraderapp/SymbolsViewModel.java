package com.example.teletraderapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.ParserConfigurationException;

public class SymbolsViewModel extends AndroidViewModel {

    private SymbolRepository repository;

    private MutableLiveData<List<Symbol>> symbols;


    public SymbolsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void loadSymbols() {
        repository = new SymbolRepository();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Symbol> loadedSymbols = repository.fetchResponse();
                    symbols.postValue(loadedSymbols);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MutableLiveData<List<Symbol>> getSymbols() {
        if (symbols == null) {
            symbols = new MutableLiveData<List<Symbol>>();
            loadSymbols();
        }
        return symbols;
    }
}
