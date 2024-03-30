package com.example.core.usecases.impl;

import com.example.core.gateway.services.FirebaseService;
import com.example.core.usecases.interfaces.Login;

public class LoginImpl implements Login {

    private FirebaseService mFirebaseService;

    public LoginImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public boolean authenticate(String email, String password) {
        return mFirebaseService.authenticate(email, password);
    }
}
