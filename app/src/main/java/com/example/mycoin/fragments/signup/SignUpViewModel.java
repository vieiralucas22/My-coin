package com.example.mycoin.fragments.signup;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.Navigation;

import com.example.mycoin.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends AndroidViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final FirebaseAuth mAuth;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void createAccount(String email, String password, View view) {
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        goLoginScreen(view);
                    } else {
                        Toast.makeText(getApplication(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goLoginScreen(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
    }
}
