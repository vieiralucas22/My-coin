package com.example.mycoin.gateway.services;

import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.entities.User;

public interface FirebaseService {
    void authenticate(String email, String password, LoginCallback loginCallback);
    void signUp(User user, RegisterCallback registerCallback);
    void createAccount(User user);
}
