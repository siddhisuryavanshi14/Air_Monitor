package com.example.airmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url=getIntent().getStringExtra("url");

        WebView browser = (WebView) findViewById(R.id.webView);
        ImageView imgBack=findViewById(R.id.imgBack);
        browser.setWebViewClient(new WebViewClient());
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String html="<iframe width=\"450\" height=\"260\" style=\"border: 1px solid #cccccc;\" src=\""+url+"\"></iframe>";
        browser.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

        imgBack.setOnClickListener(v -> onBackPressed());
    }
}