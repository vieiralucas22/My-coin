package com.example.mycoin.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class AppPreferences {

    private final SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferences(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }


}
