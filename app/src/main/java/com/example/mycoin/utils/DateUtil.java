package com.example.mycoin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {

    public static String getDateFormatted(int day, int month, int year) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return formatter.format(calendar.getTime());
    }

    public static int getCurrentYear() {
        Calendar calendar =Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static boolean isValidBirthDate(String date) {
        String[] birthData = date.split("/");

        int ano = Integer.parseInt(birthData[2]);

        return DateUtil.getCurrentYear() - ano <= 12;
    }

    public static int getDayByFormattedDate(String date) {
        String[] birthData = date.split("/");

        return Integer.parseInt(birthData[0]);
    }

    public static int getMonthByFormattedDate(String date) {
        String[] birthData = date.split("/");

        return Integer.parseInt(birthData[1]) - 1;
    }

    public static int getYearByFormattedDate(String date) {
        String[] birthData = date.split("/");

        return Integer.parseInt(birthData[2]);
    }

}
