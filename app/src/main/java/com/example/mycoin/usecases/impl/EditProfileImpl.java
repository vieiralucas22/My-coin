package com.example.mycoin.usecases.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
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
    public void editUserPassword(String oldPassword, String newPassword, String confirmPassword, ChangePasswordCallback callback) {

        User currentUser = mAppPreferences.getCurrentUser();

        Log.d(TAG, currentUser.getPassword());

        if (TextUtils.isEmpty(oldPassword)) {
            callback.onFailure(R.string.missing_old_password);
            return;
        }

        if (!oldPassword.equals(currentUser.getPassword())) {
            callback.onFailure(R.string.old_password_is_wrong);
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            callback.onFailure(R.string.missing_new_password);
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            callback.onFailure(R.string.missing_confirmation_password);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            callback.onFailure(R.string.password_are_different);
            return;
        }

        mFirebaseService.changePassword(newPassword, callback);

    }
}
