package com.example.mycoin.usecases.impl;

import android.text.TextUtils;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.Login;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class LoginImpl implements Login {
    public static final String TAG = LogcatUtil.getTag(LoginImpl.class);
    private final FirebaseService mFirebaseService;

    @Inject
    public LoginImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void authenticate(String email, String password, LoginCallback loginCallback) {
        if (TextUtils.isEmpty(email)) {
            loginCallback.onFailure(R.string.missing_email);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            loginCallback.onFailure(R.string.missing_password);
            return;
        }

        mFirebaseService.authenticate(email, password, loginCallback);
    }
}
