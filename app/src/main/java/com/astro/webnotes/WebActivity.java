package com.astro.webnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos",-1);
        String url = MainActivity.urlList.get(pos);
        Log.i("url",MainActivity.urlList.get(pos));
        webView.loadUrl(url);




    }
}
