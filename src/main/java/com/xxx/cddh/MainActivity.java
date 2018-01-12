package com.xxx.cddh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static Activity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.openWZA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(accessibleIntent);
            }
        });

        findViewById(R.id.openWindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.showPopupWindow(getApplication());
            }
        });
        ma = this;
    }
}
