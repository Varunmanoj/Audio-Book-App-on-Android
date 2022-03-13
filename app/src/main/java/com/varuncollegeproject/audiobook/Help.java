package com.varuncollegeproject.audiobook;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {
    WebView Web;
    WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//        Link Java and XML
        Web = findViewById(R.id.webview);
        webSettings = Web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Open Related Website
        String URL = "https://hereme.azurewebsites.net/";
        Web.setWebViewClient(new WebViewClient());
        Web.loadUrl(URL);
    }

    @Override
    public void onBackPressed() {
        if (Web.canGoBack()) {
            Web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
