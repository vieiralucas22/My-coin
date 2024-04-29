package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.ChangePassword;

import javax.inject.Inject;

public class ChangePasswordImpl implements ChangePassword {

    private final FirebaseService mFirebaseService;
    private final Context mContext;
    private final AppPreferences mAppPreferences;

    @Inject
    public ChangePasswordImpl(FirebaseService firebaseService, Context context,
            AppPreferences appPreferences) {
        mFirebaseService = firebaseService;
        mContext = context;
        mAppPreferences = appPreferences;
    }

    @Override
    public void sendLinkToChangeForgotPassword(int confirmationCode, ChangePasswordCallback callback) {
        if (confirmationCode != mAppPreferences.getConfirmationCode()) {
            callback.onFailure(R.string.code_wrong);
            return;
        }

        mFirebaseService.sendLink(callback);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String confirmPassword, ChangePasswordCallback callback) {

        User currentUser = mAppPreferences.getCurrentUser();

        if (TextUtils.isEmpty(oldPassword)) {
            callback.onFailure(R.string.missing_old_password);
            return;
        }

        if (!oldPassword.equals(currentUser.getPassword())) {
            callback.onFailure(R.string.old_password_is_wrong);
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            callback.onFailure(R.string.missing_new_password);
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            callback.onFailure(R.string.missing_confirmation_password);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            callback.onFailure(R.string.password_are_different);
            return;
        }

        mFirebaseService.changePassword(newPassword, callback);
    }
}
