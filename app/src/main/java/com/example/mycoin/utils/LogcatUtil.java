package com.example.mycoin.utils;

public class LogcatUtil {
    public static <T> String getTag(Class<T> clazz) {
        return "[Coin] " + clazz.getSimpleName();
    }
}
