package com.example.mycoin.fragments.login;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.usecases.interfaces.Login;
import com.example.mycoin.fragments.signup.SignUpViewModel;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private final Login mLogin;

    @Inject
    public LoginViewModel( Login login) {
        mLogin = login;
    }


    public boolean login(String email, String password) {
        return mLogin.authenticate(email, password);
    }
}