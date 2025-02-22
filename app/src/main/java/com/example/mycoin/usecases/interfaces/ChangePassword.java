package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.ChangePasswordCallback;

public interface ChangePassword {
    void sendLinkToChangeForgotPassword(int confirmationCode, ChangePasswordCallback callback);
    void changePassword(String oldPassword, String newPassword, String confirmPassword, ChangePasswordCallback callback);
}
