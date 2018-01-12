package com.xxx.cddh;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;

import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

public class WindowUtils {

    private static final String LOG_TAG = "WindowUtils";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;

    public static Boolean isShown = false;
    private static WebView webView;
    private static View contentView;

    /**
     * 显示弹出框
     *
     * @param context
     */
    public static void showPopupWindow(final Context context) {
        if (isShown) {
            return;
        }

        isShown = true;

        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        mView = setUpView(context);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // 设置flag

        int flags = FLAG_NOT_TOUCH_MODAL;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = mContext.getResources().getDimensionPixelOffset(R.dimen.windowHeight);

        params.gravity = Gravity.BOTTOM;

        mWindowManager.addView(mView, params);
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }

    }

    public static void updateUrl(String url){
        if (webView != null){
            webView.loadUrl(url);
        }

    }

    private static View setUpView(final Context context) {

        if (contentView != null){
            return contentView;
        }

        contentView = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);

        webView = new WebView(mContext);
        WebViewUtils.webViewBaseSet(webView, null, null, null);

        FrameLayout webViewFrameLayout = contentView.findViewById(R.id.webViewFrameLayout);
        webViewFrameLayout.removeAllViews();
        webViewFrameLayout.addView(webView);

        contentView.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.hidePopupWindow();
            }
        });
//        // 点击back键可消除
//        view.setOnKeyListener(new View.OnKeyListener() {
//
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    WindowUtils.hidePopupWindow();
//                    return true;
//                default:
//                    return false;
//                }
//            }
//        });

        return contentView;

    }
}