package com.xxx.cddh;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibility extends AccessibilityService {
    private static final String TAG = "MyAccessibility";

    @Override
    protected void onServiceConnected() {
    }

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (SearchResultActivity.openFlag){
            return;
        }

        try {
            AccessibilityNodeInfo noteInfo = getRootInActiveWindow();
            if (noteInfo == null) {
                return;
            } else {
                recycle(noteInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int preIndex;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void recycle(AccessibilityNodeInfo info) {
        try {
            if ((info.getClassName() + "").contains("ListView") || (info.getClassName() + "").contains("RecyclerView")){
                return;
            }
            if (info.getChildCount() == 0) {
                String text = info.getText() + "";
                if (text.matches("\\b\\d{1,2}[\\.].+[\\?\\？]*")) {
                    int currentIndex = Integer.parseInt(text.substring(0, text.indexOf(".")) + "");
                    if (preIndex != currentIndex && currentIndex > preIndex) {
                        preIndex = currentIndex;
                        Intent intent = new Intent(getApplication(), SearchResultActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("key", text.substring(text.indexOf(".") + 1));
                        startActivity(intent);
                        try {
                            SearchResultActivity.searchActivity.overridePendingTransition(0, 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        WindowUtils.updateUrl("https://zhidao.baidu.com/search?word=" + text.substring(text.indexOf(".") + 1));
                    }
                }
            } else {
                for (int i = 0; i < info.getChildCount(); i++) {
                    AccessibilityNodeInfo child = info.getChild(i);

                    if (child != null) {
                        recycle(info.getChild(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {
    }

}  