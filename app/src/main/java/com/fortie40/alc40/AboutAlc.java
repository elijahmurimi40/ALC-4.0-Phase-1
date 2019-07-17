package com.fortie40.alc40;

import android.net.http.SslError;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutAlc extends AppCompatActivity {

    private SwipeRefreshLayout mSwipe;
    private WebView mWebView;
    public static final String URL = "https://andela.com/alc/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_alc);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipe = findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWeb();
            }
        });

        loadWeb();
    }


    // Load the website to the web view
    private void loadWeb() {
        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.canGoBack();

        mWebView.setWebViewClient(new WebViewClient(){
            // No Internet Connection
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mWebView.loadUrl("file:///android_asset/error.html");
                // super.onReceivedError(view, request, error);
            }

            // Ignore SSL certificate
            // Todo:  Implement certificate pinning
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            // Remove loader
            @Override
            public void onPageFinished(WebView view, String url) {
                mSwipe.setRefreshing(false);
                // super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl(URL);
        mSwipe.setRefreshing(true);
    }
}
