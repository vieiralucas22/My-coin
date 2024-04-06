package com.example.mycoin.usecases.impl;

import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;

import javax.inject.Inject;

public class SendForgotPasswordEmailImpl implements SendForgotPasswordEmail {

    @Inject
    public SendForgotPasswordEmailImpl() {
    }

    @Override
    public void sendEmailToGetCode(String email) {

    }
}
