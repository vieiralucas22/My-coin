package com.example.mycoin.fragments.forgotpassword;

import static com.example.mycoin.utils.NavigationUtil.navigate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.databinding.FragmentConfirmCodeBinding;
import com.example.mycoin.databinding.FragmentForgotPasswordBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(ForgotPasswordFragment.class);

    private Button mButtonSendCode, mButtonBack;
    private EditText mEditEmail;
    private FragmentForgotPasswordBinding mBinding;

    private ForgotPasswordViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in forgot password fragment");

        mViewModel = getViewModel(ForgotPasswordViewModel.class);

        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mButtonSendCode = mBinding.buttonSendCode;
        mButtonBack = mBinding.buttonBack;
        mEditEmail = mBinding.editEmail;
    }

    private void initListeners() {
        mButtonSendCode.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getNeedNavigate().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                navigate(getView(), Constants.FORGOT_PASSWORD_FRAGMENT, Constants.CONFIRMATION_CODE_FRAGMENT);
                return;
            }
            MessageUtil.showToast(getContext(), R.string.email_invalid);
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_send_code) {
            String email = mEditEmail.getText().toString();

            mViewModel.sendEmailToGetConfirmCode(email);
        } else if (id == R.id.button_back) {
            backScreen(v);
        }
    }
}