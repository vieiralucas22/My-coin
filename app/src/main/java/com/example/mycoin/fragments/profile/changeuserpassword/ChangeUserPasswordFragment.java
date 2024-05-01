package com.example.mycoin.fragments.profile.changeuserpassword;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentChangeUserPasswordBinding;
import com.example.mycoin.fragments.BaseFragment;

public class ChangeUserPasswordFragment extends BaseFragment implements View.OnClickListener {

    private Button mButtonBack, mButtonEye, mButtonChangePassword;
    private EditText mEditOldPassword, mEditNewPassword, mEditConfirmPassword;
    private View mMenu;
    private ProgressBar mProgressBar;
    private FragmentChangeUserPasswordBinding mBinding;

    private ChangeUserPasswordViewModel mViewModel;

    private boolean mIsPasswordVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentChangeUserPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(ChangeUserPasswordViewModel.class);
        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mButtonBack = mBinding.buttonBack;
        mButtonEye = mBinding.buttonEyeChangePassword;
        mButtonChangePassword = mBinding.buttonChangeUserPassword;
        mEditOldPassword = mBinding.editOldPassword;
        mEditNewPassword = mBinding.editNewPasswordUser;
        mEditConfirmPassword = mBinding.editConfirmNewPasswordUser;
        mProgressBar = mBinding.progressBar;
    }

    private void initListeners() {
        mButtonEye.setOnClickListener(this);
        mButtonChangePassword.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getHandleResponseLayout().observe(getViewLifecycleOwner(), responseSuccess -> {
            responseArrivedUI();

            if (!responseSuccess) return;

            mEditOldPassword.setText("");
            mEditNewPassword.setText("");
            mEditConfirmPassword.setText("");
        });
    }

    private void changePasswordVisibility() {
        if (mIsPasswordVisible) {
            mEditOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mEditNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mEditConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mButtonEye.setBackgroundResource(R.drawable.eye_slash_icon);
        } else {
            mEditOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mEditNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mEditConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mButtonEye.setBackgroundResource(R.drawable.eye_icon);
        }

        mIsPasswordVisible = !mIsPasswordVisible;
    }

    private void waitResponseUI() {
        mButtonChangePassword.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void responseArrivedUI() {
        mButtonChangePassword.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.button_eye_change_password) {
            changePasswordVisibility();
        } else if (v.getId() == R.id.button_change_user_password) {
            String oldPassword = mEditOldPassword.getText().toString();
            String newPassword = mEditNewPassword.getText().toString();
            String confirmNewPassword = mEditConfirmPassword.getText().toString();

            waitResponseUI();
            mViewModel.changeUserPassword(oldPassword, newPassword, confirmNewPassword);
        }
    }
}