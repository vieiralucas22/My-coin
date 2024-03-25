package com.example.mycoin.services;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseService {

    private final FirebaseAuth mAuth;

    public FirebaseService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void authenticate(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              // return task.isSuccessful();
            }
        });

    }
}
