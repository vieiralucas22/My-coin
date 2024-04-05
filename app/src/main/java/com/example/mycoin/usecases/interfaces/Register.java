package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.RegisterCallback;

public interface Register {
    void signUp(String email, String password, RegisterCallback registerCallback);
}
