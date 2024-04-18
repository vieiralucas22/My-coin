package com.example.mycoin.gateway.services;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.UserDataCallback;
import com.example.mycoin.entities.User;

public interface FirebaseService {
    void authenticate(String email, String password, LoginCallback loginCallback);
    void signUp(User user, RegisterCallback registerCallback);
    void changeForgotPassword(String newPassword, ChangePasswordCallback callback);
    void sendLinkToChangeForgotPassword(ChangePasswordCallback callback);
    void getUserByEmail(String email, UserDataCallback callback);
}
