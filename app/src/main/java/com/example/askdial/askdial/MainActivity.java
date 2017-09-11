package com.example.askdial.askdial;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Handler;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wang.avi.AVLoadingIndicatorView;

import static android.view.View.GONE;
import static com.example.askdial.askdial.R.id.webView;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    Button reload;
    LinearLayout linearlayout_error;
    boolean doubleBackToExitPressedOnce = false;
    private AVLoadingIndicatorView progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reload = (Button) findViewById(R.id.btn_reload);
        myWebView = (WebView) findViewById(webView);
        progressBar = (AVLoadingIndicatorView)findViewById(R.id.   loading_bar);
        linearlayout_error= (LinearLayout) findViewById(R.id.ll_error);
        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (isInternetConnected()) {
            myWebView.loadUrl("http://askdial.com/");
            myWebView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    view.loadUrl("about:blank");
                    linearlayout_error.setVisibility(View.VISIBLE);
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progressBar.setVisibility(View.VISIBLE );
                    myWebView.setVisibility(GONE);
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                    myWebView.setVisibility(View.VISIBLE);
                    super.onPageFinished(view, url);
                }
            });
        } else {
            myWebView.setVisibility(GONE);
            linearlayout_error.setVisibility(View.VISIBLE);
            //Toast.makeText(MainActivity.this, "No Network Found", Toast.LENGTH_SHORT).show();
        }

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetConnected()) {
                    linearlayout_error.setVisibility(View.GONE);
                    myWebView.setVisibility(View.VISIBLE);
                    myWebView.loadUrl("http://askdial.com/");
                    myWebView.setWebViewClient(new WebViewClient(){
                        @Override
                        public void onReceivedError(WebView view, int errorCode,
                                                    String description, String failingUrl) {
                            view.loadUrl("about:blank");
                            linearlayout_error.setVisibility(View.VISIBLE);
                            super.onReceivedError(view, errorCode, description, failingUrl);
                        }

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            progressBar.setVisibility(View.VISIBLE );
                            myWebView.setVisibility(GONE);
                            super.onPageStarted(view, url, favicon);

                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            progressBar.setVisibility(View.GONE);
                            myWebView.setVisibility(View.VISIBLE);
                            super.onPageFinished(view, url);
                        }
                    });

                } else {
                    myWebView.setVisibility(GONE);
                    linearlayout_error.setVisibility(View.VISIBLE);
                    //Toast.makeText(MainActivity.this, "No Network Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);

                        }
                    })
                    .setNeutralButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;

                }
            }, 500);
        } else {
            super.onBackPressed();
        }
    }
}
