package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.UserDataCallback;

public interface EditProfile {
    void loadDataUser(String email, UserDataCallback callback);
    void editUserData(String email, UserDataCallback callback);
}
