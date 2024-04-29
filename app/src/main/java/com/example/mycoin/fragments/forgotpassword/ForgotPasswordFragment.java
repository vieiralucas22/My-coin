package com.example.mycoin.fragments.forgotpassword;

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
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(ForgotPasswordFragment.class);

    private Button mButtonSendCode, mButtonBack;
    private EditText mEditEmail;

    private ForgotPasswordViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
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
        mViewModel = getViewModel(ForgotPasswordViewModel.class);
        mButtonSendCode = view.findViewById(R.id.button_send_code);
        mButtonBack = view.findViewById(R.id.button_back);
        mEditEmail = view.findViewById(R.id.edit_email);
    }

    private void initListeners() {
        mButtonSendCode.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getNeedNavigate().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                goConfirmCodeScreen(getView());
                return;
            }
            MessageUtil.showToast(getContext(), R.string.email_invalid);
        });
    }

    private void goConfirmCodeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_forgotPasswordFragment_to_confirmCodeFragment);
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