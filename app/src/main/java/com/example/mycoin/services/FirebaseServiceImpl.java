package com.example.mycoin.services;

import android.util.Log;

import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.gateway.services.FirebaseService;
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

    public void authenticate(String email, String password, LoginCallback loginCallback) {
        Log.d(TAG, email);
        Log.d(TAG, password);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginCallback.onSuccess();
                Log.d(TAG, "Bateu aqui");
                return;
            }
            loginCallback.onFailure();
        });
    }
}
