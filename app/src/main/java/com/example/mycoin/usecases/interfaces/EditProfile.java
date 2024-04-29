package com.example.mycoin.usecases.interfaces;

import android.net.Uri;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.entities.User;

public interface EditProfile {
    User loadDataUser();
    void editUserData(String name, String dataBirth, UserDataChangeCallback callback);
    void uploadUserPhoto(Uri uri, UploadPhotoCallback uploadPhotoCallback);
}
