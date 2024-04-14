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
    public void changeForgotPassword(String email, ChangePasswordCallback callback) {
        if (TextUtils.isEmpty(email)) {
            MessageUtil.showToast(mContext, R.string.missing_new_password);
            return;
        }

        mFirebaseService.sendLinkToChangeForgotPassword(email, callback);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword,
                 String newPasswordConfirmed) {

    }
}
