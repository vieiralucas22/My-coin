package com.example.mycoin.fragments.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.usecases.interfaces.Login;
import com.example.mycoin.fragments.signup.SignUpViewModel;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private final Login mLogin;

    private MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();

    @Inject
    public LoginViewModel(Login login) {
        mLogin = login;
    }

    public void login(String email, String password) {
        Log.d(TAG, email);
        Log.d(TAG, password);
         mLogin.authenticate(email, password, new LoginCallback() {

            @Override
            public void onSuccess() {
                mNeedNavigate.postValue(true);
            }

            @Override
            public void onFailure() {
                mNeedNavigate.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getNeedNavigate() {
        return mNeedNavigate;
    }
}