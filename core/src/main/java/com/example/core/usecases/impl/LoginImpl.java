package com.example.core.usecases.impl;

import com.example.core.gateway.services.FirebaseService;
import com.example.core.usecases.interfaces.Login;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

public class LoginImpl implements Login {

    private FirebaseService mFirebaseService;

    @Inject
    public LoginImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public AtomicBoolean authenticate(String email, String password) {
        return mFirebaseService.authenticate(email, password);
    }
}
