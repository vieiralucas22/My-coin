package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.DateUtil;
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
    public void signUp(User user, RegisterCallback registerCallback) {
        if (TextUtils.isEmpty(user.getName())) {
            registerCallback.onFailure(R.string.missing_name);
            return;
        }

        if (TextUtils.isEmpty(user.getEmail())) {
            registerCallback.onFailure(R.string.missing_email);
            return;
        }

        if (user.getBirthDate().equals("Date of Birth")
                || DateUtil.isValidBirthDate(user.getBirthDate())) {
            registerCallback.onFailure(R.string.select_valid_date);
            return;
        }

        if (TextUtils.isEmpty(user.getPassword())) {
            registerCallback.onFailure(R.string.missing_password);
            return;
        }

        mFirebaseService.signUp(user, registerCallback);
    }

}
