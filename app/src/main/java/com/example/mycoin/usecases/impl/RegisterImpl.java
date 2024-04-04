package com.example.mycoin.usecases.impl;

import android.text.TextUtils;

import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class RegisterImpl implements Register {

    private static final String TAG = LogcatUtil.getTag(RegisterImpl.class);

    private FirebaseService mFirebaseService;
    //private Context mContext;

    @Inject
    public RegisterImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
        //mContext = context;
    }

    @Override
    public void signUp(String email, String password, String dateBirth, String username) {
        if (TextUtils.isEmpty(email)) {
            // MessageUtil.showToast(mContext, R.string.missing_email);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //MessageUtil.showToast(mContext, R.string.missing_password);
            return;
        }

        mFirebaseService;
    }
}
