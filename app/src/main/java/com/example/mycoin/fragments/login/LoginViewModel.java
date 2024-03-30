package com.example.mycoin.fragments.login;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.core.usecases.impl.LoginImpl;
import com.example.core.usecases.interfaces.Login;
import com.example.mycoin.fragments.signup.SignUpViewModel;
import com.example.mycoin.services.FirebaseServiceImpl;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final FirebaseServiceImpl mFirebaseService;
    private final Login mLogin;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mFirebaseService = new FirebaseServiceImpl();
        mLogin = new LoginImpl();
    }


    public boolean login(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Email field is empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplication(), "Password field is empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return mLogin.authenticate(email, password);
    }
}