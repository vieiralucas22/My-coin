package com.example.mycoin.fragments.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LogcatUtil.getTag(LoginFragment.class);

    private TextView mTextForgotPassword, mTextSignUp;
    private Button mButtonLogin, mButtonEye;
    private LoginViewModel mViewModel;
    private EditText mEditPassword, mEditEmail;

    private boolean mIsPasswordVisible = false;

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
        initObservers();
    }

    private void initComponents(View view) {
        mViewModel = getViewModel(LoginViewModel.class);
        mTextForgotPassword = view.findViewById(R.id.text_forgot_password_login);
        mTextSignUp = view.findViewById(R.id.text_sign_up_login);
        mButtonLogin = view.findViewById(R.id.button_login);
        mButtonEye = view.findViewById(R.id.button_eye_login);
        mEditPassword = view.findViewById(R.id.edit_password_login);
        mEditEmail = view.findViewById(R.id.edit_email_login);

        initListeners();
    }

    private void initListeners() {
        mTextForgotPassword.setOnClickListener(this);
        mTextSignUp.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mButtonEye.setOnClickListener(this);
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

    private void changePasswordVisibility() {
        if (mIsPasswordVisible) {
            mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mButtonEye.setBackgroundResource(R.drawable.eye_slash_icon);
        } else {
            mEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mButtonEye.setBackgroundResource(R.drawable.eye_icon);
        }

        mIsPasswordVisible = !mIsPasswordVisible;
    }

    private void initObservers() {
        mViewModel.getNeedNavigate().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                goHomeScreen(getView());
            }
        });
    }

    private void login(View v) {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();

        mViewModel.login(email, password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy!");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.text_forgot_password_login) {
            goForgotPasswordScreen(v);
        } else if (id == R.id.text_sign_up_login) {
            goSignUpScreen(v);
        } else if (id == R.id.button_login) {
            login(v);
        } else if (id == R.id.button_eye_login) {
            changePasswordVisibility();
        }
    }
}
