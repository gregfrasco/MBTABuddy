package com.mbtabuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import mbta.mbtabuddy.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //Get the web view reference
        final WebView wv = (WebView) findViewById(R.id.web_view);

        //Get the url to open if it's there
        Intent myIntent = getIntent();
        if(myIntent.hasExtra("url"))
        {
            String url = myIntent.getStringExtra("url");
            wv.getSettings().setJavaScriptEnabled(true);
            wv.loadUrl(url);
        }

        //Set up the back button click event
        Button goBackBtn = (Button) findViewById(R.id.back_web_btn);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wv.copyBackForwardList().getCurrentIndex() > 0) {
                    wv.goBack();
                }
            }
        });
    }
}
