package com.example.mycoin.services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseService {
    private static final String TAG = FirebaseService.class.getSimpleName();

    private final FirebaseAuth mAuth;

    public FirebaseService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void authenticate(String email, String password) {
        Log.d(TAG, mAuth.signInWithEmailAndPassword(email, password).getResult().getUser().getEmail());
    }
}
