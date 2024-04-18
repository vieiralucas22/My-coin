package com.example.mycoin.callbacks;

import com.example.mycoin.entities.User;

public interface UserDataCallback {
    void OnSuccess(User user);
    void OnFailure();
}
