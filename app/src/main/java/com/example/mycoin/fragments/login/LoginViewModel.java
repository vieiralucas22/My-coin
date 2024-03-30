package com.example.mycoin.fragments.login;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

import com.example.core.gateway.services.FirebaseService;
import com.example.core.usecases.interfaces.Login;
import com.example.mycoin.fragments.signup.SignUpViewModel;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final FirebaseService mFirebaseService;
    private final Login mLogin;

    @Inject
    public LoginViewModel(FirebaseService firebaseService, Login login) {
        mFirebaseService = firebaseService;
        mLogin = login;
    }


    public boolean login(String email, String password) {
        return mLogin.authenticate(email, password).get();
    }
}