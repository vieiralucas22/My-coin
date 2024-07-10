package com.example.mycoin.fragments.home;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName();

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
        mFirebaseService.setUserByEmail();
    }

    public String getUserName() {
        return mAppPreferences.getCurrentUser().getName();
    }

    public String getUserPoints() {
        return mAppPreferences.getCurrentUser().getPoints() + " points";
    }
}