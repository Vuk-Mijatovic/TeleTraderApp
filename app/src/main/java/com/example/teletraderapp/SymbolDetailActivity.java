package com.example.teletraderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class SymbolDetailActivity extends AppCompatActivity {
    Date date;

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
        String decorativeName = item.getDecorativeName();
        String isin = item.getIsin();

        double chg = item.getChg();
        double last = item.getLast();
        double bid = item.getBid();
        double ask = item.getAsk();
        double high = item.getHigh();
        double low = item.getLow();
        double changePercent = item.getChangePercent();
        double volume = item.getVolume();

        String dateAndTime = item.getDateAndTime();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss");
        try {
             date = parser.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm");
        String dateAndTimeFormated = dateFormat.format(date);




        setTitle(name);

        TextView tickerSymbolView = findViewById(R.id.ticker_symbol);
        tickerSymbolView.setText(tickerSymbol);

        TextView stockExcangeNameView = findViewById(R.id.stock_exchange_name);
        stockExcangeNameView.setText(stockExchangeName);

        TextView currencyView = findViewById(R.id.currency);
        currencyView.setText(currency);

        TextView dateView = findViewById(R.id.date_time);
        dateView.setText(dateAndTimeFormated);

        TextView chgView = findViewById(R.id.change_value);
        chgView.setText(formatValue(chg));
        if (chg > 0) chgView.setTextColor(Color.parseColor("#1faa00"));
        else if (chg < 0) chgView.setTextColor(Color.RED);
        else chgView.setTextColor(Color.WHITE);

        TextView lastView = findViewById(R.id.last);
        lastView.setText(formatValue(last));

        TextView bidView = findViewById(R.id.bid);
        bidView.setText("Bid: " + formatValue(bid));

        TextView askView = findViewById(R.id.ask);
        askView.setText("Ask: " + formatValue(ask));

        TextView highView = findViewById(R.id.high_detail_view);
        highView.setText("High: " + formatValue(high));

        TextView lowView = findViewById(R.id.low_detail_view);
        lowView.setText("Low: " + formatValue(low));

        TextView changePercentView = findViewById(R.id.chg_percent_value);
        changePercentView.setText(formatValue(changePercent));
        if (changePercent > 0) changePercentView.setTextColor(Color.parseColor("#1faa00"));
        else if (changePercent < 0) changePercentView.setTextColor(Color.RED);
        else changePercentView.setTextColor(Color.WHITE);

        TextView volumeView = findViewById(R.id.volume);
        volumeView.setText(formatValue(volume));

        TextView decorativeNameView = findViewById(R.id.decorative_name);
        decorativeNameView.setText(decorativeName);

        TextView isinView = findViewById(R.id.isin_value);
        isinView.setText(isin);
    }

    private String formatValue(double value) {
        return String.format("%.2f", value);
    }
}