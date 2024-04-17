package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.mycoin.R;
import com.example.mycoin.gateway.services.EmailService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;
import com.example.mycoin.utils.MessageUtil;

import java.util.Random;

import javax.inject.Inject;

public class SendForgotPasswordEmailImpl implements SendForgotPasswordEmail {

    private final EmailService mEmailService;
    private final AppPreferences mAppPreferences;
    private final Context mContext;

    @Inject
    public SendForgotPasswordEmailImpl(EmailService emailService,
            AppPreferences appPreferences, Context context) {
        mEmailService = emailService;
        mAppPreferences = appPreferences;
        mContext = context;
    }

    @Override
    public boolean trySendEmailToGetCode(String email) {
        if (TextUtils.isEmpty(email)) {
            MessageUtil.showToast(mContext, R.string.missing_email);
            return false;
        }

        generateConfirmationCode();
        return mEmailService.sendForgotPasswordEmail(email);
    }

    private void generateConfirmationCode() {
        Random random = new Random();

        int confirmationCode = random.nextInt(8999) + 1000;

        mAppPreferences.setConfirmationCode(confirmationCode);
    }
}
