package com.example.mycoin.usecases.impl;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.example.mycoin.gateway.services.EmailService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;

import java.util.Random;

import javax.inject.Inject;

public class SendForgotPasswordEmailImpl implements SendForgotPasswordEmail {

    private final EmailService mEmailService;
    private final AppPreferences mAppPreferences;

    @Inject
    public SendForgotPasswordEmailImpl(EmailService emailService,
            AppPreferences appPreferences) {
        mEmailService = emailService;
        mAppPreferences = appPreferences;
    }

    @Override
    public boolean trySendEmailToGetCode(String email) {
        generateConfirmationCode();
        return mEmailService.sendForgotPasswordEmail(email);
    }

    private void generateConfirmationCode() {
        Random random = new Random();

        int confirmationCode = random.nextInt(8999) + 1000;

        mAppPreferences.setConfirmationCode(confirmationCode);
    }
}
