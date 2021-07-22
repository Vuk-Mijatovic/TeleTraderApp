package com.example.teletraderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends AppCompatActivity {

    List<Symbol> symbols = new ArrayList<>();
    SymbolAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SymbolsViewModel viewModel = new ViewModelProvider(this).get(SymbolsViewModel.class);
        try {
            viewModel.getList();
        } catch (Exception e) {
            //todo handle error
            e.printStackTrace();
        }
        symbols = viewModel.getSymbols();
        adapter = new SymbolAdapter(symbols);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ascending:
                Collections.sort(symbols, new Comparator<Symbol>() {
                    @Override
                    public int compare(Symbol o1, Symbol o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });
                adapter.notifyDataSetChanged();


                break;
            case R.id.descending:
                Collections.sort(symbols, new Comparator<Symbol>() {
                    @Override
                    public int compare(Symbol o1, Symbol o2) {
                        return o2.getName().compareToIgnoreCase(o1.getName());
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case R.id.unsorted:
                Collections.shuffle(symbols);
                adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}