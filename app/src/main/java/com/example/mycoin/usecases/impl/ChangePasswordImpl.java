package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.ChangePassword;
import com.example.mycoin.utils.MessageUtil;

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
    public void changeForgotPassword(int confirmationCode, ChangePasswordCallback callback) {
        if (confirmationCode != mAppPreferences.getConfirmationCode()) {
            callback.onFailure(R.string.code_wrong);
            return;
        }

        mFirebaseService.sendLinkToChangeForgotPassword(callback);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword,
                 String newPasswordConfirmed) {

    }
}
