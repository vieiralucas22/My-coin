package com.example.mycoin.services;

import android.util.Log;

import com.example.core.gateway.services.FirebaseService;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

public class FirebaseServiceImpl implements FirebaseService {
    private static final String TAG = LogcatUtil.getTag(FirebaseServiceImpl.class);

    private final FirebaseAuth mAuth;

    @Inject
    public FirebaseServiceImpl(FirebaseAuth auth) {
        mAuth = auth;
    }

    public AtomicBoolean authenticate(String email, String password) {
        AtomicBoolean x = new AtomicBoolean();
        Log.d(TAG, "Email: " + email);
        Log.d(TAG, "Password: " + password);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            x.set(task.isSuccessful());
            Log.d(TAG, "Atomic -> " + task.isSuccessful());
        });
        Log.d(TAG, "Atomic -> " + x.get());
        return x;
    }
}
