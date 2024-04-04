package com.example.mycoin.fragments.signup;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.mycoin.R;
import com.example.mycoin.usecases.interfaces.Register;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final Register mRegister;
    @Inject
    public SignUpViewModel(Register register) {
        mRegister = register;
    }

    protected void createAccount(String email, String password, String date, String username) {
        mRegister.signUp(email, password, date, username);
    }

}
