package com.example.mycoin.usecases.impl;

import android.util.Log;

import com.example.mycoin.callbacks.UserDataCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.fragments.home.HomeFragment;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class EditProfileImpl implements EditProfile {
    public static final String TAG = LogcatUtil.getTag(EditProfileImpl.class);

    private FirebaseService mFirebaseService;
    private final AppPreferences mAppPreferences;

    @Inject
    public EditProfileImpl(FirebaseService firebaseService, AppPreferences appPreferences) {
        mFirebaseService = firebaseService;
        mAppPreferences = appPreferences;
    }

    @Override
    public User loadDataUser(String email) {
        return mAppPreferences.getCurrentUser();
    }

    @Override
    public void editUserData(String email, UserDataCallback callback) {

    }
}
