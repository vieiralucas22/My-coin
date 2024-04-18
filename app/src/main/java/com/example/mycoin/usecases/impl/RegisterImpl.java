package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.DateUtil;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import java.util.Calendar;

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
            MessageUtil.showToast(mContext, R.string.missing_name);
            registerCallback.onFailure();
            return;
        }

        if (TextUtils.isEmpty(user.getEmail())) {
            MessageUtil.showToast(mContext, R.string.missing_email);
            registerCallback.onFailure();
            return;
        }

        if (user.getBirthDate().equals("Date of Birth") || isValidDate(user.getBirthDate())) {
            MessageUtil.showToast(mContext, R.string.select_valid_date);
            registerCallback.onFailure();
            return;
        }

        if (TextUtils.isEmpty(user.getPassword())) {
            MessageUtil.showToast(mContext, R.string.missing_password);
            registerCallback.onFailure();
            return;
        }

        mFirebaseService.signUp(user, registerCallback);
    }

    private boolean isValidDate(String date) {
        String[] birthData = date.split("/");

        int ano = Integer.parseInt(birthData[2]);

        return DateUtil.getCurrentYear() - ano <= 12;
    }
}
