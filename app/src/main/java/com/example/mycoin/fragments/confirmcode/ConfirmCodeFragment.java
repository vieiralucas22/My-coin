package com.example.mycoin.fragments.confirmcode;

import static com.example.mycoin.utils.NavigationUtil.navigate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.databinding.FragmentConfirmCodeBinding;
import com.example.mycoin.databinding.FragmentEditUserProfileBinding;
import com.example.mycoin.databinding.FragmentGeneralProfileBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.login.LoginFragment;
import com.example.mycoin.utils.LogcatUtil;

public class ConfirmCodeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LogcatUtil.getTag(LoginFragment.class);

    private Button mButtonConfirm, mButtonBack;
    private EditText mEditTextFieldOne, mEditTextFieldTwo, mEditTextFieldThree , mEditTextFieldFour;
    private ConfirmCodeViewModel mViewModel;
    private ProgressBar mProgressBar;
    private FragmentConfirmCodeBinding mBinding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentConfirmCodeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in confirm code fragment");

        mViewModel = getViewModel(ConfirmCodeViewModel.class);

        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mButtonConfirm = mBinding.buttonConfirm;
        mButtonBack = mBinding.buttonBack;
        mEditTextFieldOne = mBinding.editCodeOne;
        mEditTextFieldTwo = mBinding.editCodeTwo;
        mEditTextFieldThree = mBinding.editCodeThree;
        mEditTextFieldFour = mBinding.editCodeFour;
        mProgressBar = mBinding.progressBar;;
    }

    private void initListeners() {
        mButtonConfirm.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mEditTextFieldOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditTextFieldTwo.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEditTextFieldTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditTextFieldThree.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mEditTextFieldThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditTextFieldFour.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void initObservers() {
        mViewModel.getNeedNavigate().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                navigate(getView(), Constants.CONFIRMATION_CODE_FRAGMENT, Constants.LOGIN_FRAGMENT);
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

    private int confirmationCodeFormatted() {
        String fieldOne = mEditTextFieldOne.getText().toString();
        String fieldTwo = mEditTextFieldTwo.getText().toString();
        String fieldThree = mEditTextFieldThree.getText().toString();
        String fieldFour = mEditTextFieldFour.getText().toString();

        if (mViewModel.codeIsValid(fieldOne, fieldTwo, fieldThree, fieldFour)) {
            String confirmationCode = fieldOne + fieldTwo + fieldThree + fieldFour;
            return Integer.parseInt(confirmationCode);
        }

        return -1;
    }

    private void waitResponseUI() {
        mButtonConfirm.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void responseArrivedUI() {
        mButtonConfirm.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_confirm) {
            mViewModel.setUpUIToWaitResponse();
            mViewModel.confirmCode(confirmationCodeFormatted());
        } else if (id == R.id.button_back) {
            backScreen(v);
        }
    }
}