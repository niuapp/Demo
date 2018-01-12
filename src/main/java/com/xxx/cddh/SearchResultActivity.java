package com.xxx.cddh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Created by niuapp on 2018/1/12 22:23.
 * Project : Demo.
 * Email : 345485985@qq.com
 * -->
 */

public class SearchResultActivity extends AppCompatActivity {

    public static SearchResultActivity searchActivity;

    public static boolean openFlag = false;
    private static View contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = this;

        try {
            MainActivity.ma.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentView == null) {
            contentView = View.inflate(getApplication(), R.layout.activity_search, null);
        }else {
            try {
                ((ViewGroup)contentView.getParent()).removeView(contentView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setContentView(contentView);

        FrameLayout wf = (FrameLayout) findViewById(R.id.webViewFrameLayout);
        WebView webView = new WebView(this);
        WebViewUtils.webViewBaseSet(webView, null, null, null);
        wf.addView(webView);

        webView.loadUrl("https://www.baidu.com/s?wd=" + getIntent().getStringExtra("key"));
        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openFlag = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        openFlag = false;
    }
}
