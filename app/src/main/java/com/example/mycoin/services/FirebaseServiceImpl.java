package com.example.mycoin.services;

import android.util.Log;

import com.example.core.gateway.services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseServiceImpl implements FirebaseService {
    private static final String TAG = FirebaseServiceImpl.class.getSimpleName();

    private final FirebaseAuth mAuth;

    public FirebaseServiceImpl() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean authenticate(String email, String password) {
        Log.d(TAG, "Result: " + mAuth.signInWithEmailAndPassword(email, password).getResult());
        Log.d(TAG, "User: " + mAuth.signInWithEmailAndPassword(email, password).getResult().getUser());
        Log.d(TAG, mAuth.signInWithEmailAndPassword(email, password).getResult().getUser().getEmail());

        return true;
    }
}
