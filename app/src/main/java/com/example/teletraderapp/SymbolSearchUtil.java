package com.example.teletraderapp;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SymbolSearchUtil {
    private static final String XML_URL = "https://www.teletrader.rs/downloads/tt_symbol_list.xml";


    public static String makeAHttpRequest() throws IOException {
        String  XMLResponse = "";
        URL url = new URL(XML_URL);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            String credentials = "android_tt" + ":" + "Sk3M!@p9e";
            String base64EncodedCredentials = android.util.Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            String authHeaderValue = "Basic " + base64EncodedCredentials;
            urlConnection.setRequestProperty("Authorization", authHeaderValue);
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* milliseconds */);
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();

                XMLResponse = readFromStream(inputStream);


            } else {
                Log.e("SymbolSearchUtil", "Error code:" + urlConnection.getResponseCode());
            }


        } catch (IOException e) {
            Log.e("SymbolSearchUtil", "Problem retrieving JSON response.");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return XMLResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
