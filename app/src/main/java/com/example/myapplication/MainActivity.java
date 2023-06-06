package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    //Initialize Application Context
    private WebView webView;
    private static Context appContext;

    //Get Application Context (for use in external functions)
    public static Context getContext() {
        return appContext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the WebView by its unique ID
        webView = findViewById(R.id.web);
        // loading https://www.geeksforgeeks.org url in the WebView.


        // this will enable the javascript.
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new AndroidInterface(MainActivity.this), "androidInterface");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectJQueryCode();
                testJQueryFunctionality();
            }
        });

        webView.loadUrl("https://shoppalclub.com/");

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.

    }
    private void injectJQueryCode() {
        webView.evaluateJavascript(loadJQueryCode(), null);
    }
    private String loadJQueryCode() {
        try {
            InputStream inputStream = getAssets().open("jquery.min.js");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            reader.close();
            inputStream.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private void testJQueryFunctionality() {
        webView.evaluateJavascript(
                "(function() {" +
                        "var script = document.createElement('script');" +
                        "script.innerHTML = 'if (typeof jQuery !== \"undefined\") { $(\"#carouselExampleControls\").hide(); } else { window.androidInterface.onJQueryLoaded(false); }';" +
                        "document.head.appendChild(script);" +
                        "})();",
                null
        );
    }
}

