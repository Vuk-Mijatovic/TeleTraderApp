package com.example.teletraderapp;


import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SymbolRepository {

    private static final String XML_URL = "https://www.teletrader.rs/downloads/tt_symbol_list.xml";
    RequestQueue requestQueue;

    public SymbolRepository(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchResponse() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, XML_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.i("Response is", response);
                } else {
                    Log.i("Response is", "No response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Response is", error.toString());
            }
       }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String credentials = "android_tt" + ":" + "Sk3M!@p9e";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }



}


