package com.example.mycoin.fragments.confirmcode;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.login.LoginFragment;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

public class ConfirmCodeFragment extends BaseFragment implements View.OnClickListener, View.OnKeyListener {

    public static final String TAG = LogcatUtil.getTag(LoginFragment.class);

    private Button mButtonConfirm, mButtonBack;
    private EditText mEditTextFieldOne, mEditTextFieldTwo, mEditTextFieldThree , mEditTextFieldFour;
    private ConfirmCodeViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in confirm code fragment");

        initComponents(view);
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mViewModel = getViewModel(ConfirmCodeViewModel.class);
        mButtonConfirm = view.findViewById(R.id.button_confirm);
        mButtonBack = view.findViewById(R.id.button_back);
        mEditTextFieldOne = view.findViewById(R.id.edit_code_one);
        mEditTextFieldTwo = view.findViewById(R.id.edit_code_two);
        mEditTextFieldThree = view.findViewById(R.id.edit_code_three);
        mEditTextFieldFour = view.findViewById(R.id.edit_code_four);
    }

    private void initListeners() {
        mButtonConfirm.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mEditTextFieldOne.setOnKeyListener(this);
        mEditTextFieldTwo.setOnKeyListener(this);
        mEditTextFieldThree.setOnKeyListener(this);
    }

    private void initObservers() {
        mViewModel.getNeedNavigate().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                goLogin(getView());
                return;
            }
            MessageUtil.showToast(getContext(), R.string.code_wrong);
        });
    }

    private void goLogin(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_confirmCodeFragment_to_loginFragment);
    }

    private int formatConfirmationCode() {
        String fieldOne = mEditTextFieldOne.getText().toString();
        String fieldTwo = mEditTextFieldTwo.getText().toString();
        String fieldThree = mEditTextFieldThree.getText().toString();
        String fieldFour = mEditTextFieldFour.getText().toString();

        String confirmationCode = fieldOne + fieldTwo + fieldThree + fieldFour;
        return Integer.parseInt(confirmationCode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_confirm) {
            mViewModel.confirmCode(formatConfirmationCode());
        } else if (id == R.id.button_back) {
            backScreen(v);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            if (v.getId() == R.id.edit_code_one) {
                mEditTextFieldTwo.requestFocus();
                return true;
            } else if (v.getId() == R.id.edit_code_two) {
                mEditTextFieldThree.requestFocus();
                return true;
            } else if (v.getId() == R.id.edit_code_three) {
                mEditTextFieldFour.requestFocus();
                return true;
            }
        }
        return false;
    }
}