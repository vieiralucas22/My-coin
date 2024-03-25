package com.example.mycoin.fragments.login;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mycoin.fragments.signup.SignUpViewModel;
import com.example.mycoin.services.FirebaseService;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final FirebaseService mFirebaseService;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mFirebaseService = new FirebaseService();
    }


    public void login(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Email field is empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplication(), "Password field is empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseService.authenticate(email, password);
    }
}