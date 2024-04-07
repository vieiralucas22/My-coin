package com.example.mycoin.fragments.forgotpassword;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class ForgotPasswordViewModel extends ViewModel {

    public static final String TAG = LogcatUtil.getTag(ForgotPasswordViewModel.class);

    private final SendForgotPasswordEmail mSendForgotPasswordEmail;
    @Inject
    public ForgotPasswordViewModel(SendForgotPasswordEmail sendForgotPasswordEmail) {
        mSendForgotPasswordEmail = sendForgotPasswordEmail;
    }

    public void sendEmailToGetConfirmCode(String email) {
        mSendForgotPasswordEmail.sendEmailToGetCode(email);
    }
}