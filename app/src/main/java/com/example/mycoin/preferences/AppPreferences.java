package com.example.mycoin.preferences;

import static com.example.mycoin.constants.Constants.CONFIRMATION_CODE_KEY;
import static com.example.mycoin.constants.Constants.REMEMBER_ME;
import static com.example.mycoin.constants.Constants.USER_EMAIL;

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

    public void setUserEmail(String value) {
        mSharedPreferences.edit().putString(USER_EMAIL, value).apply();
    }

    public String getUserEmail() {
        return mSharedPreferences.getString(USER_EMAIL, "");
    }

    public void removeUserEmail() {
        mSharedPreferences.edit().remove(USER_EMAIL).apply();
    }

    public void setRememberMe(Boolean isChecked) {
        mSharedPreferences.edit().putBoolean(REMEMBER_ME, isChecked).apply();
    }

    public Boolean getRememberMe() {
        return mSharedPreferences.getBoolean(REMEMBER_ME, false);
    }

    public void removeRememberMe() {
        mSharedPreferences.edit().remove(REMEMBER_ME).apply();
    }
}
