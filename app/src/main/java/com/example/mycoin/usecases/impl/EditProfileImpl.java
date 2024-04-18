package com.example.mycoin.usecases.impl;

import com.example.mycoin.callbacks.UserDataCallback;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.usecases.interfaces.EditProfile;

import javax.inject.Inject;

public class EditProfileImpl implements EditProfile {

    private FirebaseService mFirebaseService;

    @Inject
    public EditProfileImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void loadDataUser(String email, UserDataCallback callback) {
        mFirebaseService.getUserByEmail(email, callback);
    }
}
