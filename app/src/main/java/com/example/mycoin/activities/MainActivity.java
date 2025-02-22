package com.example.mycoin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.mycoin.NotificationHelper;
import com.example.mycoin.R;
import com.example.mycoin.receivers.LocaleChangeReceiver;
import com.example.mycoin.utils.LogcatUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = LogcatUtil.getTag(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper.createNotificationChannel(getApplicationContext());

        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        LocaleChangeReceiver receiver = new LocaleChangeReceiver();
        registerReceiver(receiver, filter);
    }
}