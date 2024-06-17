package com.example.mycoin.gateway.services;

import android.net.Uri;

import com.example.mycoin.callbacks.Callback;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;

import java.util.List;

public interface FirebaseService {
    void authenticate(String email, String password, LoginCallback loginCallback);
    void signUp(User user, RegisterCallback registerCallback);
    void changePassword(String newPassword, ChangePasswordCallback callback);
    void sendLink(ChangePasswordCallback callback);
    void setUserByEmail(String email);
    void editUser(String name, String dataBirth, UserDataChangeCallback callback);
    void uploadPhoto(Uri uri, UploadPhotoCallback uploadPhotoCallback);
    void updateUser(User user);
    void getAllClasses(LoadClassesCallback callback);
}
