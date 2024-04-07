package com.example.mycoin.preferences;

import static com.example.mycoin.constants.Constants.CONFIRMATION_CODE_KEY;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class AppPreferences {

    private final SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferences(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public void setConfirmationCode(int value) {
        mSharedPreferences.edit().putInt(CONFIRMATION_CODE_KEY, value).apply();
    }

    public int getConfirmationCode() {
        return mSharedPreferences.getInt(CONFIRMATION_CODE_KEY, 0);
    }

    public void removeConfirmationCode() {
         mSharedPreferences.edit().remove(CONFIRMATION_CODE_KEY).apply();
    }
}
