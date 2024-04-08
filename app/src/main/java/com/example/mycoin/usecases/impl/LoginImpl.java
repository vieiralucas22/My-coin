package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.fragments.login.LoginFragment;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.Login;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import org.checkerframework.checker.units.qual.A;

import javax.inject.Inject;

public class LoginImpl implements Login {
    public static final String TAG = LogcatUtil.getTag(LoginImpl.class);
    private final FirebaseService mFirebaseService;
    private final Context mContext;

    @Inject
    public LoginImpl(FirebaseService firebaseService, Context context) {
        mFirebaseService = firebaseService;
        mContext = context;
    }

    @Override
    public void authenticate(String email, String password, LoginCallback loginCallback) {
        if (TextUtils.isEmpty(email)) {
            MessageUtil.showToast(mContext, R.string.missing_email);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            MessageUtil.showToast(mContext, R.string.missing_password);
            return;
        }



        mFirebaseService.authenticate(email, password, loginCallback);
    }
}
