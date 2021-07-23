package com.example.teletraderapp;


import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SymbolRepository {

    private static final String XML_URL = "https://www.teletrader.rs/downloads/tt_symbol_list.xml";

    RequestQueue requestQueue;

    public List<Symbol> fetchResponse() throws IOException, SAXException, ParserConfigurationException {
        String name;
        String tickerSymbol;
        String stockExchangeName;
        String currency;
        String dateAndTime;
        String decorativeName;
        String isin;
        double changePercent;
        double bid;
        double ask;
        double chg;
        double last;
        double high;
        double low;
        double volume;
        List<Symbol> list = new ArrayList<>();
        SymbolSearchUtil searchUtil = new SymbolSearchUtil();
        String response = searchUtil.makeAHttpRequest();
        if (response != null) {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));
            NodeList symbolList = document.getElementsByTagName("Symbol");
            NodeList changeList = document.getElementsByTagName("Quote");

            for (int i = 0; i < symbolList.getLength(); i++) {
                NamedNodeMap symbolAttributes = symbolList.item(i).getAttributes();
                Node nameNode = symbolAttributes.getNamedItem("name");
                if (nameNode != null) {
                    name = nameNode.getTextContent();
                } else {
                    name = "";
                }
                Node tickerSymbolNode = symbolAttributes.getNamedItem("tickerSymbol");
                if (tickerSymbolNode != null) {
                    tickerSymbol = tickerSymbolNode.getTextContent();
                } else {
                    tickerSymbol = "";
                }
                Node stockExchangeNameNode = symbolAttributes.getNamedItem("stockExchangeName");
                if (stockExchangeNameNode != null) {
                    stockExchangeName = stockExchangeNameNode.getTextContent();
                } else {
                    stockExchangeName = "";
                }
                Node currencyNode = symbolAttributes.getNamedItem("currency");
                if (currencyNode != null) {
                    currency = currencyNode.getTextContent();
                } else {
                    currency = "";
                }

                Node decorativeNameNode = symbolAttributes.getNamedItem("decorativeName");
                if (decorativeNameNode != null) {
                    decorativeName = decorativeNameNode.getTextContent();
                } else {
                    decorativeName = "";
                }
                Node isinNode = symbolAttributes.getNamedItem("isin");
                if(isinNode != null) {
                    isin = isinNode.getTextContent();
                } else {
                    isin = "";
                }

                NamedNodeMap quoteAttributes = changeList.item(i).getAttributes();

                Node dateAndTimeNode = quoteAttributes.getNamedItem("dateTime");
                if (dateAndTimeNode != null) {
                    dateAndTime = dateAndTimeNode.getTextContent();
                } else {
                    dateAndTime = "";
                }

                Node chgNode = quoteAttributes.getNamedItem("change");
                if (chgNode != null) {
                    chg = Double.parseDouble(chgNode.getTextContent());
                } else {
                    chg = Double.MIN_VALUE;
                }
                Node changePercentNode = quoteAttributes.getNamedItem("changePercent");
                if (changePercentNode != null) {
                    changePercent = Double.parseDouble(changePercentNode.getTextContent());
                } else {
                    changePercent = Double.MIN_VALUE;
                }
                Node volumeNode = quoteAttributes.getNamedItem("volume");
                if (volumeNode != null) {
                    volume = Double.parseDouble(volumeNode.getTextContent());
                } else {
                    volume = Long.MIN_VALUE;
                }
                Node lastNode = quoteAttributes.getNamedItem("last");
                if (chgNode != null) {
                    last = Double.parseDouble(lastNode.getTextContent());
                } else {
                    last = Double.MIN_VALUE;
                }
                Node bidNode = quoteAttributes.getNamedItem("bid");
                if (bidNode != null) {
                    bid = Double.parseDouble(bidNode.getTextContent());
                } else {
                    bid = Double.MIN_VALUE;
                }
                Node askNode = quoteAttributes.getNamedItem("ask");
                if (askNode != null) {
                    ask = Double.parseDouble(askNode.getTextContent());
                } else {
                    ask = Double.MIN_VALUE;
                }
                Node highNode = quoteAttributes.getNamedItem("high");
                if (highNode != null) {
                    high = Double.parseDouble(highNode.getTextContent());
                } else {
                    high = Double.MIN_VALUE;
                }
                Node lowNode = quoteAttributes.getNamedItem("low");
                if (lowNode != null) {
                    low = Double.parseDouble(lowNode.getTextContent());
                } else {
                    low = Double.MIN_VALUE;
                }
                Symbol symbol = new Symbol(name, chg, last, bid, ask, high, low, tickerSymbol,
                        stockExchangeName, currency,
                        dateAndTime, changePercent, volume, decorativeName, isin);
                list.add(symbol);
            }
        }
        return list;
    }


}



