package com.example.mycoin.usecases.interfaces;

import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.entities.User;

public interface Register {
    void signUp(User user, RegisterCallback registerCallback);
}
