package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.ChangePasswordCallback;

public interface ChangePassword {
    void changeForgotPassword(String email, ChangePasswordCallback callback);

    void changePassword(String oldPassword, String newPassword, String newPasswordConfirmed);
}
