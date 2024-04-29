package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.net.Uri;

import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class EditProfileImpl implements EditProfile {
    public static final String TAG = LogcatUtil.getTag(EditProfileImpl.class);

    private FirebaseService mFirebaseService;
    private final AppPreferences mAppPreferences;
    private final Context mContext;

    @Inject
    public EditProfileImpl(FirebaseService firebaseService, AppPreferences appPreferences, Context context) {
        mFirebaseService = firebaseService;
        mAppPreferences = appPreferences;
        mContext = context;
    }

    @Override
    public User loadDataUser() {
        return mAppPreferences.getCurrentUser();
    }

    @Override
    public void editUserData(String name, String dataBirth, UserDataChangeCallback callback) {
        mFirebaseService.editUser(name, dataBirth, callback);
    }

    @Override
    public void uploadUserPhoto(Uri uri, UploadPhotoCallback uploadPhotoCallback) {
        mFirebaseService.uploadPhoto(uri, uploadPhotoCallback);
    }
}
