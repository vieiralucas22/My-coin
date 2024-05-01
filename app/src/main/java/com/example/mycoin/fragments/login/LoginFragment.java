package com.example.mycoin.fragments.login;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentLoginBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LogcatUtil.getTag(LoginFragment.class);

    private TextView mTextForgotPassword, mTextSignUp;
    private Button mButtonLogin, mButtonEye;
    private LoginViewModel mViewModel;
    private EditText mEditPassword, mEditEmail;
    private CheckBox mCheckRememberMe;
    private ProgressBar mProgressBar;
    private FragmentLoginBinding mBinding;

    private boolean mIsPasswordVisible = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(LoginViewModel.class);

        if (mViewModel.rememberMeWasChecked()) goHomeScreen(view);

        Log.d(TAG, "Enter in login fragment");

        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mTextForgotPassword = mBinding.textForgotPasswordLogin;
        mTextSignUp = mBinding.textSignUpLogin;
        mButtonLogin = mBinding.buttonLogin;
        mButtonEye = mBinding.buttonEyeLogin;
        mEditPassword = mBinding.editPasswordLogin;
        mEditEmail = mBinding.editEmailLogin;
        mCheckRememberMe = mBinding.checkboxRememberMe;
        mProgressBar = mBinding.progressBar;
    }

    private void initListeners() {
        mTextForgotPassword.setOnClickListener(this);
        mTextSignUp.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mButtonEye.setOnClickListener(this);
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
                responseArrivedUI();
            }
        });

        mViewModel.getHandleResponseLayout().observe(getViewLifecycleOwner(), responseSuccess -> {
            if (responseSuccess) {
                waitResponseUI();
                return;
            }
            responseArrivedUI();
        });
    }

    private void login() {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();

        mViewModel.setUpUIToWaitResponse();
        mViewModel.login(email, password, mCheckRememberMe.isChecked());
    }

    private void waitResponseUI() {
        mButtonLogin.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void responseArrivedUI() {
        mButtonLogin.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
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
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.text_forgot_password_login) {
            goForgotPasswordScreen(v);
        } else if (id == R.id.text_sign_up_login) {
            goSignUpScreen(v);
        } else if (id == R.id.button_login) {
            login();
        } else if (id == R.id.button_eye_login) {
            changePasswordVisibility();
        }
    }
}
