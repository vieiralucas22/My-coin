package com.example.mycoin.utils;

import android.util.Log;

public  class Logger {
    public static final String MAIN_TAG = "[MY COIN]";

    public static void d(String tag, String message) {
        Log.d(tag, MAIN_TAG + " " + message);
    }
}
