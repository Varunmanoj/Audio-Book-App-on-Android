package com.varuncollegeproject.audiobook;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

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

//        Oppen the Github Readme Linkin the App
        String URL = "https://github.com/VarunKumar-Xaviers/Audio-Book-App-on-Android/blob/a668566c59dea91fa3ed4ee92f37fdc8fe7b909e/README.md";
        Web.loadUrl(URL);
    }
}
