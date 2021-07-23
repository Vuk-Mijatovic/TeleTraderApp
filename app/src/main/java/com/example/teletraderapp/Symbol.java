package com.example.teletraderapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Symbol implements Parcelable {

    private String name;
    private String tickerSymbol;
    private String stockExchangeName;
    private String currency;
    private String dateAndTime;
    private double chg;
    private double last;
    private double bid;
    private double ask;
    private double high;
    private double low;
    private double changePercent;
    private double volume;

    protected Symbol(Parcel in) {
        name = in.readString();
        tickerSymbol = in.readString();
        stockExchangeName = in.readString();
        currency = in.readString();
        dateAndTime = in.readString();
        chg = in.readDouble();
        last = in.readDouble();
        bid = in.readDouble();
        ask = in.readDouble();
        high = in.readDouble();
        low = in.readDouble();
        changePercent = in.readDouble();
        volume = in.readDouble();
    }

    public static final Creator<Symbol> CREATOR = new Creator<Symbol>() {
        @Override
        public Symbol createFromParcel(Parcel in) {
            return new Symbol(in);
        }

        @Override
        public Symbol[] newArray(int size) {
            return new Symbol[size];
        }
    };

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getStockExchangeName() {
        return stockExchangeName;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public double getVolume() {
        return volume;
    }public String getName() {
        return name;
    }

    public double getChg() {
        return chg;
    }

    public double getLast() {
        return last;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }





    public Symbol(String name, double chg, double last, double bid,
                  double ask, double high, double low, String tickerSymbol,
                  String stockExchangeName, String currency,
                  String dateAndTime, double changePercent, double volume) {

        this.name = name;
        this.chg = chg;
        this.last = last;
        this.bid = bid;
        this.ask = ask;
        this.high = high;
        this.low = low;
        this.tickerSymbol = tickerSymbol;
        this.stockExchangeName = stockExchangeName;
        this.currency = currency;
        this.dateAndTime = dateAndTime;
        this.changePercent = changePercent;
        this.volume = volume;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(tickerSymbol);
        dest.writeString(stockExchangeName);
        dest.writeString(currency);
        dest.writeString(dateAndTime);
        dest.writeDouble(chg);
        dest.writeDouble(last);
        dest.writeDouble(bid);
        dest.writeDouble(ask);
        dest.writeDouble(high);
        dest.writeDouble(low);
        dest.writeDouble(changePercent);
        dest.writeDouble(volume);
    }
}
