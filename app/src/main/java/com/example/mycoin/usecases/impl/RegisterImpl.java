package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class RegisterImpl implements Register {

    private static final String TAG = LogcatUtil.getTag(RegisterImpl.class);

    private FirebaseService mFirebaseService;
    private Context mContext;

    @Inject
    public RegisterImpl(FirebaseService firebaseService, Context context) {
        mFirebaseService = firebaseService;
        mContext = context;
    }

    @Override
    public void signUp(String email, String password, RegisterCallback registerCallback) {
        Log.d(TAG, email);
        Log.d(TAG, password);
        if (TextUtils.isEmpty(email)) {
             MessageUtil.showToast(mContext, R.string.missing_email);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            MessageUtil.showToast(mContext, R.string.missing_password);
            return;
        }

        mFirebaseService.signUp(email, password, registerCallback);
    }
}
