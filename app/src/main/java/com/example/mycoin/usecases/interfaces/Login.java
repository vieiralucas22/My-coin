package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.LoginCallback;

public interface Login {
    void authenticate(String email, String password, LoginCallback loginCallback);
}
