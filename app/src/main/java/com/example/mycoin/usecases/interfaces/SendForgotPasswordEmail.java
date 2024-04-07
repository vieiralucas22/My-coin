package com.example.mycoin.usecases.interfaces;

public interface SendForgotPasswordEmail {
    boolean trySendEmailToGetCode(String email);
}
