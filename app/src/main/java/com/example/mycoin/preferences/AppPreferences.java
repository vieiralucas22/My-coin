package com.example.mycoin.preferences;

import static com.example.mycoin.constants.Constants.CONFIRMATION_CODE_KEY;
import static com.example.mycoin.constants.Constants.CURRENT_USER_BIRTH;
import static com.example.mycoin.constants.Constants.CURRENT_USER_EMAIL;
import static com.example.mycoin.constants.Constants.CURRENT_USER_NAME;
import static com.example.mycoin.constants.Constants.CURRENT_USER_PASSWORD;
import static com.example.mycoin.constants.Constants.REMEMBER_ME;
import static com.example.mycoin.constants.Constants.USER_EMAIL;
import static com.example.mycoin.constants.Constants.USER_PASSWORD_PREFERENCES;

import android.content.SharedPreferences;

import com.example.mycoin.entities.User;

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

    public void setUserPassword(String value) {
        mSharedPreferences.edit().putString(USER_PASSWORD_PREFERENCES, value).apply();
    }

    public String getUserPassword() {
        return mSharedPreferences.getString(USER_PASSWORD_PREFERENCES, "");
    }

    public void removeUserPassword() {
        mSharedPreferences.edit().remove(USER_PASSWORD_PREFERENCES).apply();
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

    public void setCurrentUser(User currentUser) {
        mSharedPreferences.edit().putString(CURRENT_USER_NAME, currentUser.getName()).apply();
        mSharedPreferences.edit().putString(CURRENT_USER_BIRTH, currentUser.getBirthDate()).apply();
        mSharedPreferences.edit().putString(CURRENT_USER_EMAIL, currentUser.getEmail()).apply();
        mSharedPreferences.edit().putString(CURRENT_USER_PASSWORD, getUserPassword()).apply();
    }

    public User getCurrentUser() {
        User user = new User();
        user.setName(mSharedPreferences.getString(CURRENT_USER_NAME, ""));
        user.setEmail(mSharedPreferences.getString(CURRENT_USER_EMAIL, ""));
        user.setBirthDate(mSharedPreferences.getString(CURRENT_USER_BIRTH, ""));
        user.setPassword(mSharedPreferences.getString(CURRENT_USER_PASSWORD, ""));
        return user;
    }

    public void removeCurrentUser() {
        mSharedPreferences.edit().remove(CURRENT_USER_NAME).apply();
        mSharedPreferences.edit().remove(CURRENT_USER_BIRTH).apply();
        mSharedPreferences.edit().remove(CURRENT_USER_EMAIL).apply();
        mSharedPreferences.edit().remove(CURRENT_USER_PASSWORD).apply();
    }
}
