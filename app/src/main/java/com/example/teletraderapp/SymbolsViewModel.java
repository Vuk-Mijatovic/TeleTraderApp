package com.example.teletraderapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SymbolsViewModel extends AndroidViewModel {

    private SymbolRepository repository;

    private List<Symbol> symbols;


    public SymbolsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void getList() throws Exception {
        repository = new SymbolRepository();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Symbol>> list = executor.submit(new Callable<List<Symbol>>() {
            @Override
            public List<Symbol> call() throws Exception {
                return repository.fetchResponse();
            }
        });
        symbols = list.get();
        String string = "jkjh";
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }
}
