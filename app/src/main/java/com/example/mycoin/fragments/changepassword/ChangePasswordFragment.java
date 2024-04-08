package com.example.mycoin.fragments.changepassword;

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

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.login.LoginFragment;
import com.example.mycoin.utils.MessageUtil;

public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private Button mButtonBack, mButtonChangePassword, mButtonEye;
    private EditText mEditNewPassword, mEditConfirmedPassword;
    private ChangePasswordViewModel mViewModel;

    private boolean mIsPasswordVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in forgot password fragment");

        initComponents(view);
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mViewModel = getViewModel(ChangePasswordViewModel.class);
        mButtonBack = view.findViewById(R.id.button_back);
        mButtonChangePassword = view.findViewById(R.id.button_change_password);
        mButtonEye = view.findViewById(R.id.button_eye_change_password);
        mEditNewPassword = view.findViewById(R.id.edit_new_password);
        mEditConfirmedPassword = view.findViewById(R.id.edit_confirm_new_password);
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mButtonChangePassword.setOnClickListener(this);
        mButtonEye.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getNeedNavigate().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                goLoginScreen();
                return;
            }
            MessageUtil.showToast(getContext(), R.string.code_wrong);
        });
    }

    private void changePasswordVisibility() {
        if (mIsPasswordVisible) {
            mEditNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mEditConfirmedPassword.setTransformationMethod(
                    PasswordTransformationMethod.getInstance());
            mButtonEye.setBackgroundResource(R.drawable.eye_slash_icon);
        } else {
            mEditNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mEditConfirmedPassword.setTransformationMethod(
                    HideReturnsTransformationMethod.getInstance());
            mButtonEye.setBackgroundResource(R.drawable.eye_icon);
        }

        mIsPasswordVisible = !mIsPasswordVisible;
    }

    private void goLoginScreen() {
        if (getView() == null) return;

        Navigation.findNavController(getView())
                .navigate(R.id.action_changePasswordFragment_to_loginFragment);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_back) {
            backScreen(v);
        } else if (id == R.id.button_change_password) {
            String newPassword = mEditNewPassword.getText().toString();
            String newConfirmedPassword = mEditConfirmedPassword.getText().toString();

            mViewModel.changePassword(newPassword, newConfirmedPassword);
        } else if (id == R.id.button_eye_change_password) {
            changePasswordVisibility();
        }
    }
}