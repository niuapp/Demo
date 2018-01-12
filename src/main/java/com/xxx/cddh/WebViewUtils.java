package com.xxx.cddh;

import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Administrator on 2015/12/11.
 * WebView 延展出的 工具类
 */
public class WebViewUtils {

    private static View errorPage;

    public interface OnWebViewShouldOverrideUrlLoadingListener {
        void shouldOverrideUrlLoading(WebView view, String url);
    }

    /**
     * WebView 基本设置
     *
     * @param webView
     * @param loadingPage
     */
    public static void webViewBaseSet(final WebView webView, final View loadingPage, final View errorPage, final OnWebViewShouldOverrideUrlLoadingListener shouldOverrideUrlLoading) {


        final Context context = webView.getContext();

        final WebSettings settings = webView.getSettings();

        //不使用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

////        关闭硬件加速
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }


        if (Build.VERSION.SDK_INT >= 16) {
            // 跨域
            settings.setAllowUniversalAccessFromFileURLs(true);
        }

        //图片不自动加载，在网页加载完成后才加载
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        //JS可用
        settings.setJavaScriptEnabled(true);
        //
        webView.getSettings().setDomStorageEnabled(true);
        //给webView 关联 需要JS调用的类
        // 需要 new新的，每个WebView对应不同的MyWebChromeClient对象
        //可以通过触摸获取焦点
        webView.requestFocusFromTouch();
        //页面大小自适应
        settings.setLoadWithOverviewMode(true);
//        //背景透明
        webView.setBackgroundColor(Color.alpha(0x00111111));


        //在WebView中加载后续页面
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (shouldOverrideUrlLoading != null) {
                    shouldOverrideUrlLoading.shouldOverrideUrlLoading(view, url);
                }else {
                    view.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载完成开启图片自动加载
                if (!settings.getLoadsImagesAutomatically()) {
                    settings.setLoadsImagesAutomatically(true);
                }

                // 隐藏加载中页面
                if (loadingPage != null) {
                    loadingPage.setVisibility(View.GONE);
                    loadingPage.clearAnimation();
                }

            }
        });


    }
}
