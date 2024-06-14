package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;


public class ApiQuotes extends AppCompatActivity {

    TextView quoteTextView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_quotes);
        quoteTextView = findViewById(R.id.quoteText);
        fetchQuote();


    }
    private void fetchQuote() {

        url = "https://api.adviceslip.com/advice?" + System.currentTimeMillis();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject slipObject = response.getJSONObject("slip");
                            String advice = slipObject.getString("advice");

                            quoteTextView.setText('"'+advice+'"');
                        } catch (Exception e) {
                            quoteTextView.setText("Invalid Target");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        quoteTextView.setText("Fetching ERROR");
                    }
                });


        Volley.newRequestQueue(this).add(request);
    }

    public void refreshTapped(View view) {
        fetchQuote();

    }
}