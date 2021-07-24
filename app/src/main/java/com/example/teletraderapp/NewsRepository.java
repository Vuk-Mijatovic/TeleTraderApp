package com.example.teletraderapp;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class NewsRepository {

    public static final String NEWS_URL = "https://www.teletrader.rs/downloads/tt_news_list.xml";

    public List<News> fetchResponse() throws IOException, ParserConfigurationException, SAXException {
        String headline;
        int imageId;
        List<News> news = new ArrayList<>();

        SymbolSearchUtil searchUtil = new SymbolSearchUtil();
        String response = searchUtil.makeAHttpRequest(NEWS_URL);

        if (response != null) {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = newDocumentBuilder.parse(new ByteArrayInputStream(response.getBytes()));
            NodeList headlineList = document.getElementsByTagName("Headline");
            NodeList imageIdList = document.getElementsByTagName("ImageID");
            for (int i = 0; i < headlineList.getLength(); i++) {
                if (headlineList.item(i) != null)
                    headline = headlineList.item(i).getTextContent();
                else headline = "";
                if (imageIdList.item(i) != null)
                    imageId = Integer.valueOf(imageIdList.item(i).getTextContent());
                else imageId = Integer.MIN_VALUE;
                news.add(new News(headline, imageId));
            }
        }
        return news;
    }

}
