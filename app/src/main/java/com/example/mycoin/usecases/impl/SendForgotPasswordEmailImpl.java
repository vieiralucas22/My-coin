package com.example.mycoin.usecases.impl;

import com.example.mycoin.gateway.services.EmailService;
import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;

import javax.inject.Inject;

public class SendForgotPasswordEmailImpl implements SendForgotPasswordEmail {

    private final EmailService mEmailService;

    @Inject
    public SendForgotPasswordEmailImpl(EmailService emailService) {
        mEmailService = emailService;
    }

    @Override
    public void sendEmailToGetCode(String email) {


        mEmailService.sendForgotPasswordEmail(email);
    }
}
