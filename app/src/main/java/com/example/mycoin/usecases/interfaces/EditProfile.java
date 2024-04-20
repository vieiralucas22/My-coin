package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.UserDataCallback;
import com.example.mycoin.entities.User;

public interface EditProfile {
    User loadDataUser(String email);
    void editUserData(String email, UserDataCallback callback);
}
