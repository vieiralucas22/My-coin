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
import com.example.mycoin.fragments.BaseFragment;

public class ChangeUserPasswordFragment extends BaseFragment implements View.OnClickListener {

    private Button mButtonBack, mButtonEye, mButtonChangePassword;
    private EditText mEditOldPassword, mEditNewPassword, mEditConfirmPassword;
    private View mMenu;
    private ProgressBar mProgressBar;

    private ChangeUserPasswordViewModel mViewModel;

    private boolean mIsPasswordVisible = false;

    public static ChangeUserPasswordFragment newInstance() {
        return new ChangeUserPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(ChangeUserPasswordViewModel.class);
        initComponents(view);
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mButtonBack = view.findViewById(R.id.button_back);
        mButtonEye = view.findViewById(R.id.button_eye_change_password);
        mButtonChangePassword = view.findViewById(R.id.button_change_user_password);
        mEditOldPassword = view.findViewById(R.id.edit_old_password);
        mEditNewPassword = view.findViewById(R.id.edit_new_password_user);
        mEditConfirmPassword = view.findViewById(R.id.edit_confirm_new_password_user);
        mProgressBar = view.findViewById(R.id.progressBar);
    }

    private void initListeners() {
        mButtonEye.setOnClickListener(this);
        mButtonChangePassword.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getHandleResponseLayout().observe(getViewLifecycleOwner(), responseSuccess -> {
            if (!responseSuccess) {
                responseArrivedUI();
                return;
            }
            mEditOldPassword.setText("");
            mEditNewPassword.setText("");
            mEditConfirmPassword.setText("");
            responseArrivedUI();
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

    private void awaitResponseUI() {
        mButtonChangePassword.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void responseArrivedUI() {
        mButtonChangePassword.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
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

            awaitResponseUI();
            mViewModel.changeUserPassword(oldPassword, newPassword, confirmNewPassword);
        }
    }
}