package com.example.mycoin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class LocaleChangeReceiver extends BroadcastReceiver {
    private static final String TAG = LogcatUtil.getTag(LocaleChangeReceiver.class);

    @Inject
    FirebaseService firebaseService;

    @Override
    public void onReceive(Context context, Intent intent) {

        DaggerAppComponent.builder()
                .applicationModule(new AppModule(context))
                .build()
                .inject(this);

        if (Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
            updateFirebaseData();
        }
    }

    private void updateFirebaseData() {
        firebaseService.updateDataLanguage();
    }
}