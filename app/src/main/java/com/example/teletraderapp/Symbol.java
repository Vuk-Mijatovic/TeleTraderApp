package com.example.teletraderapp;

public class Symbol {
    private String name;
    private double chg;
    private double last;
    private double bid;
    private double ask;
    private double high;
    private double low;

    public Symbol(String name, double chg, double last, double bid, double ask, double high, double low) {
        this.name = name;
        this.chg = chg;
        this.last = last;
        this.bid = bid;
        this.ask = ask;
        this.high = high;
        this.low = low;
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
