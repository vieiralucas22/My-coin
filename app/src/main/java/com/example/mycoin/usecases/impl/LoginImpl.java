package com.example.mycoin.usecases.impl;

import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.Login;

import javax.inject.Inject;

public class LoginImpl implements Login {

    private FirebaseService mFirebaseService;

    @Inject
    public LoginImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public boolean authenticate(String email, String password) {
        return mFirebaseService.authenticate(email, password);
    }
}
