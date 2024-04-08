package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.ChangePassword;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class ChangePasswordImpl implements ChangePassword {


    private final FirebaseService mFirebaseService;
    private final Context mContext;

    @Inject
    public ChangePasswordImpl(FirebaseService firebaseService, Context context) {
        mFirebaseService = firebaseService;
        mContext = context;
    }

    @Override
    public void changeForgotPassword(String newPassword, String newPasswordConfirmed,
                 ChangePasswordCallback callback) {
        if (TextUtils.isEmpty(newPassword)) {
            MessageUtil.showToast(mContext, R.string.missing_new_password);
            return;
        }

        if (TextUtils.isEmpty(newPasswordConfirmed)) {
            MessageUtil.showToast(mContext, R.string.missing_confirmation_password);
            return;
        }

        if (!newPassword.equals(newPasswordConfirmed)) {
            MessageUtil.showToast(mContext, R.string.password_are_different);
            return;
        }

        mFirebaseService.changeForgotPassword(newPassword, callback);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword,
                 String newPasswordConfirmed) {

    }
}
