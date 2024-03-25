package com.example.mycoin.fragments.login;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.mycoin.R;
import com.example.mycoin.fragments.signup.SignUpViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final FirebaseAuth mAuth;

    private MutableLiveData<Boolean> mLoginSuccessful = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Login successful!");
                        mLoginSuccessful.setValue(true);
                        return;
                    }
                    mLoginSuccessful.setValue(false);
                });
    }

    protected LiveData<Boolean> getLoginSuccessful() {
        return mLoginSuccessful;
    }
}