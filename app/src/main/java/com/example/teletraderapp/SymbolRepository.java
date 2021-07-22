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
        double bid;
        double ask;
        double chg;
        double last;
        double high;
        double low;
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
                NamedNodeMap quoteAttributes = changeList.item(i).getAttributes();
                Node chgNode = quoteAttributes.getNamedItem("change");
                if (chgNode != null) {
                    chg = Double.parseDouble(chgNode.getTextContent());
                } else {
                    chg = Double.MIN_VALUE;
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
                Symbol symbol = new Symbol(name, chg, last, bid, ask, high, low);
                list.add(symbol);
            }
        }
        return list;
    }

//    public void fetchResponse() {
//        List<Symbol> list = new ArrayList<>();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, XML_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                String name;
//                double bid;
//                double ask;
//                double chg;
//                double last;
//                double high;
//                double low;
//                if (response != null) {
//                    try {
//                        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                        Document document = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));
//                        NodeList symbolList = document.getElementsByTagName("Symbol");
//                        NodeList changeList = document.getElementsByTagName("Quote");
//
//                        for (int i = 0; i < symbolList.getLength(); i++) {
//                            NamedNodeMap symbolAttributes = symbolList.item(i).getAttributes();
//                            Node nameNode = symbolAttributes.getNamedItem("name");
//                            if (nameNode != null) {
//                                name = nameNode.getTextContent();
//                            } else {
//                                name = "";
//                            }
//                            NamedNodeMap quoteAttributes = changeList.item(i).getAttributes();
//                            Node chgNode = quoteAttributes.getNamedItem("change");
//                            if (chgNode != null){
//                             chg = Double.parseDouble(chgNode.getTextContent());}
//                            else {
//                                chg = Double.MIN_VALUE;
//                            }
//                            Node lastNode = quoteAttributes.getNamedItem("last");
//                            if (chgNode != null){
//                                last = Double.parseDouble(lastNode.getTextContent());}
//                            else {
//                                last = Double.MIN_VALUE;
//                            }
//                            Node bidNode = quoteAttributes.getNamedItem("bid");
//                            if ( bidNode != null) {
//                                bid = Double.parseDouble(bidNode.getTextContent());
//                            } else{
//                                bid = Double.MIN_VALUE;
//                            }
//                            Node askNode = quoteAttributes.getNamedItem("ask");
//                            if (askNode != null) {
//                                ask = Double.parseDouble(askNode.getTextContent());
//                            } else {
//                                ask = Double.MIN_VALUE;
//                            }
//                            Node highNode = quoteAttributes.getNamedItem("high");
//                            if (highNode != null) {
//                                high = Double.parseDouble(highNode.getTextContent());
//                            } else {
//                                high = Double.MIN_VALUE;
//                            }
//                            Node lowNode = quoteAttributes.getNamedItem("low");
//                            if (lowNode != null) {
//                                low = Double.parseDouble(lowNode.getTextContent());
//                            } else {
//                                low = Double.MIN_VALUE;
//                            }
//                            Symbol symbol = new Symbol(name, chg, last, bid, ask, high, low);
//                            list.add(symbol);
//                        }
//                    } catch (Exception e) {
//                        //todo Handle error
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //todo handle error
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() {
//
//                String credentials = "android_tt" + ":" + "Sk3M!@p9e";
//                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Basic " + base64EncodedCredentials);
//                return headers;
//            }
//        };
//        requestQueue.add(stringRequest);
//
//    }

}



