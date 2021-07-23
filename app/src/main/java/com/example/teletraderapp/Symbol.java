package com.example.teletraderapp;

public class Symbol {
    private String name;
    private String tickerSymbol;

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

    public long getVolume() {
        return volume;
    }

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
    private long volume;


    public Symbol(String name, double chg, double last, double bid,
                  double ask, double high, double low, String tickerSymbol,
                  String stockExchangeName, String currency,
                  String dateAndTime, double changePercent, long volume) {

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

    public String getName() {
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


}
