package com.example.mycoin.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    public FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public FirebaseFirestore providesFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }
}
