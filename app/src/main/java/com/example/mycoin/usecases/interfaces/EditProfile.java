package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.entities.User;

public interface EditProfile {
    User loadDataUser();
    void editUserData(String name, String dataBirth, UserDataChangeCallback callback);
    void editUserPassword(String oldPassword, String newPassword, String confirmPassword, ChangePasswordCallback callback);
}
