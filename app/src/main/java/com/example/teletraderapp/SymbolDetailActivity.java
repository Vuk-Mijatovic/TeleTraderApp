package com.example.teletraderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SymbolDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_detail);

        Intent intent = getIntent();
        Symbol item = intent.getParcelableExtra("Symbol");

        String name = item.getName();
        String tickerSymbol = item.getTickerSymbol();
        String stockExchangeName = item.getStockExchangeName();
        String currency = item.getCurrency();

        double chg = item.getChg();
        double last = item.getLast();
        double bid = item.getBid();
        double ask = item.getAsk();
        double high = item.getHigh();
        double low = item.getLow();
        double changePercent = item.getChangePercent();
        double volume = item.getVolume();

        String dateAndTime = item.getDateAndTime();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'");
        try {
            Date date = parser.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        setTitle(name);

        TextView tickerSymbolView = findViewById(R.id.ticker_symbol);
        tickerSymbolView.setText(tickerSymbol);

        TextView stockExcangeNameView = findViewById(R.id.stock_exchange_name);
        stockExcangeNameView.setText(stockExchangeName);

        TextView currencyView = findViewById(R.id.currency);
        currencyView.setText(currency);

        TextView dateView = findViewById(R.id.date_time);
        dateView.setText(dateAndTime);
        Log.i("Details", "Date is " + dateAndTime);

        TextView chgView = findViewById(R.id.change_value);
        chgView.setText(String.valueOf(chg));

        TextView lastView = findViewById(R.id.last);
        lastView.setText(String.valueOf(last));

        TextView bidView = findViewById(R.id.bid);
        bidView.setText(String.valueOf(bid));

        TextView askView = findViewById(R.id.ask);
        askView.setText(String.valueOf(ask));

        TextView highView = findViewById(R.id.high_detail_view);
        highView.setText(String.valueOf(high));

        TextView lowView = findViewById(R.id.low_detail_view);
        lowView.setText(String.valueOf(low));

        TextView changePercentView = findViewById(R.id.chg_percent_value);
        changePercentView.setText(String.valueOf(changePercent));

        TextView volumeView = findViewById(R.id.volume);
        volumeView.setText(String.valueOf(volume));



    }
}