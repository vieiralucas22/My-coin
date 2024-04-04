package com.example.mycoin.gateway.services;

import com.example.mycoin.callbacks.LoginCallback;

public interface FirebaseService {
    void authenticate(String email, String password, LoginCallback loginCallback);
    void signUp(String email, String password, String dateBirth, String username);
}
