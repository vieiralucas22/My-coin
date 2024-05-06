package com.example.mycoin.fragments.home;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private final FirebaseAuth mAuth;
    private final FirebaseService mFirebaseService;
    private final AppPreferences mAppPreferences;

    @Inject
    public HomeViewModel(FirebaseAuth auth, FirebaseService firebaseService,
            AppPreferences appPreferences) {
        mAuth = auth;
        mFirebaseService = firebaseService;
        mAppPreferences = appPreferences;
    }

    public void setCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) return;

        mFirebaseService.setUserByEmail(user.getEmail());
    }

    public String getUserName() {
        return mAppPreferences.getCurrentUser().getName();
    }
}