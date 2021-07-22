package com.example.teletraderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
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
    SwipeRefreshLayout swipeLayout;
    SymbolsViewModel viewModel;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(SymbolsViewModel.class);
        try {
            viewModel.getList();
        } catch (Exception e) {
            //todo handle error
            e.printStackTrace();
        }
        symbols = viewModel.getSymbols();
        adapter = new SymbolAdapter(symbols);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeLayout.setRefreshing(false);
                //todo Check this code
            }
        });


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
            case R.id.refresh:
                refreshData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            symbols.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };

    private void refreshData() {
        adapter = null;
        try {
            viewModel.getList();
        } catch (Exception e) {
            //todo handle error
            e.printStackTrace();
        }
        symbols = viewModel.getSymbols();
        adapter = new SymbolAdapter(symbols);
        recyclerView.setAdapter(adapter);
    }

}