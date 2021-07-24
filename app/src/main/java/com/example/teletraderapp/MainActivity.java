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
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;
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


public class MainActivity extends AppCompatActivity implements SymbolAdapter.OnItemClickListener {

    SymbolAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    SymbolsViewModel viewModel;
    RecyclerView recyclerView;
    boolean isChangeLastView;
    TextView changeLabelView;
    TextView lastLabelView;
    public static final int UNSORTED = 0;
    public static final int ASCENDING = 1;
    public static final int DESCENDING = 2;
    public static final String SORTING_ORDER = "Sorting order";
    public static final String LIST_FORMAT = "List Format";
    private int sortingOrder = UNSORTED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        isChangeLastView = sharedPref.getBoolean(LIST_FORMAT, true);
        sortingOrder = sharedPref.getInt(SORTING_ORDER, UNSORTED);

        changeLabelView = findViewById(R.id.chg_label);
        lastLabelView = findViewById(R.id.last_header_label);

        if (!isChangeLastView) {
            changeLabelView.setText("Bid/Ask");
            lastLabelView.setText("High/Low");
        }
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            viewModel = new ViewModelProvider(this).get(SymbolsViewModel.class);
            viewModel.getSymbols().observe(this, symbols -> {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
                if (sortingOrder == ASCENDING) {
                    sortAscending(symbols);
                }
                if (sortingOrder == DESCENDING) {
                    sortDescending(symbols);
                }
                adapter = new SymbolAdapter(symbols, this, isChangeLastView);
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
                    }
                });
            });

        } else
            Toast.makeText(this, "No internet connection. Please try later.", Toast.LENGTH_SHORT).show();
    }

    private void sortDescending(List<Symbol> symbols) {
        Collections.sort(symbols, new Comparator<Symbol>() {
            @Override
            public int compare(Symbol o1, Symbol o2) {
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
        });
    }

    private void sortAscending(List<Symbol> symbols) {
        Collections.sort(symbols, new Comparator<Symbol>() {
            @Override
            public int compare(Symbol o1, Symbol o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
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
        List<Symbol> symbols = viewModel.getSymbols().getValue();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (item.getItemId()) {
            case R.id.ascending:
                sortingOrder = ASCENDING;


                editor.putInt(SORTING_ORDER, ASCENDING);
                editor.apply();
                sortAscending(symbols);
                viewModel.getSymbols().postValue(symbols);
                break;

            case R.id.descending:
                sortingOrder = DESCENDING;
                editor = sharedPref.edit();
                editor.putInt(SORTING_ORDER, DESCENDING);
                editor.apply();
                viewModel.getSymbols().postValue(symbols);
                sortDescending(symbols);
                break;

            case R.id.unsorted:
                sortingOrder = UNSORTED;
                editor.putInt(SORTING_ORDER, UNSORTED);
                editor.apply();
                Collections.shuffle(symbols);
                viewModel.getSymbols().postValue(symbols);

            case R.id.refresh:
                refreshData();
                break;

            case R.id.change_last:
                isChangeLastView = true;
                editor.putBoolean(LIST_FORMAT, isChangeLastView);
                editor.apply();
                changeLabelView.setText("Chg%");
                lastLabelView.setText("Last");
                adapter.setIsChangeLastView(isChangeLastView);
                adapter.notifyDataSetChanged();
                break;

            case R.id.bid_ask:
                isChangeLastView = false;
                editor.putBoolean(LIST_FORMAT, isChangeLastView);
                editor.apply();
                changeLabelView.setText("Bid/Ask");
                lastLabelView.setText("High/Low");
                adapter.setIsChangeLastView(isChangeLastView);
                adapter.notifyDataSetChanged();
                break;
            case R.id.info:
                String url = "https://drive.google.com/file/d/1J2GZFjQJD9M4cVhtXOKlqHkVdEAGmuCz/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.news:
                Intent newsIntent = new Intent(this, NewsActivity.class);
                startActivity(newsIntent);
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
            List<Symbol> symbols = viewModel.getSymbols().getValue();
            symbols.remove(viewHolder.getAdapterPosition());
            viewModel.getSymbols().postValue(symbols);
        }
    };

    private void refreshData() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            viewModel.loadSymbols();
        } else {
            Toast.makeText(this, "No internet connection. Please try later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Symbol item) {
        Intent intent = new Intent(this, SymbolDetailActivity.class);
        intent.putExtra("Symbol", item);
        startActivity(intent);
    }
}