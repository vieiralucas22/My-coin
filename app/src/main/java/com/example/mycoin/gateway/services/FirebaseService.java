package com.example.mycoin.gateway.services;

import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;

public interface FirebaseService {
    void authenticate(String email, String password, LoginCallback loginCallback);
    void signUp(String email, String password, RegisterCallback registerCallback);
}
