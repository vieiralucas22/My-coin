package com.example.mycoin.utils;

import android.content.Context;
import android.widget.Toast;

public class MessageUtil {
    public static void showToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
