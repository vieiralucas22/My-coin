package com.example.mycoin.fragments.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private TextView mTextForgotPassword, mTextSignUp;
    private Button mButtonLogin;
    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in login fragment");

        initComponents(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initComponents(View view) {
        mTextForgotPassword = view.findViewById(R.id.text_forgot_password_login);
        mTextSignUp = view.findViewById(R.id.text_sign_up_login);
        mButtonLogin = view.findViewById(R.id.button_login);

        initListeners();
    }

    private void initListeners() {
        mTextForgotPassword.setOnClickListener(this);
        mTextSignUp.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    private void goForgotPasswordScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
    }

    private void goSignUpScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.text_forgot_password_login) {
            goForgotPasswordScreen(v);
        } else if (id == R.id.text_sign_up_login) {
            goSignUpScreen(v);
        } else if (id == R.id.button_login) {
            goHomeScreen(v);
        }
    }
}
