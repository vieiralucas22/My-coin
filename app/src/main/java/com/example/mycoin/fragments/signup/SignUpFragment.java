package com.example.mycoin.fragments.signup;

import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentForgotPasswordBinding;
import com.example.mycoin.databinding.FragmentHomeBinding;
import com.example.mycoin.databinding.FragmentSignUpBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.DateUtil;
import com.example.mycoin.utils.LogcatUtil;

import java.util.Calendar;

public class SignUpFragment extends BaseFragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    public static final String TAG = LogcatUtil.getTag(SignUpFragment.class);

    private Button mButtonBack, mButtonDatePicker, mButtonEye, mButtonSignUp;
    private TextView mTextDate;
    private CardView mCardDatePicker;
    private EditText mEditPassword, mEditEmail, mEditName;
    private ProgressBar mProgressBar;
    private FragmentSignUpBinding mBinding;

    private boolean mIsPasswordVisible = false;

    private SignUpViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in sign up fragment");

        mViewModel = getViewModel(SignUpViewModel.class);

        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mButtonBack = mBinding.buttonBack;
        mCardDatePicker = mBinding.cardDate;
        mTextDate = mBinding.textDate;
        mButtonDatePicker = mBinding.buttonCalendar;
        mButtonEye = mBinding.buttonEye;
        mEditPassword = mBinding.editPassword;
        mButtonSignUp = mBinding.buttonSign;
        mEditEmail = mBinding.editEmailSignUp;
        mEditName = mBinding.editUsername;
        mProgressBar = mBinding.progressBar;
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mCardDatePicker.setOnClickListener(this);
        mButtonDatePicker.setOnClickListener(this);
        mButtonEye.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getSignUpSuccessful().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                goLoginScreen();
                responseArrivedUI();
            }
        });


        mViewModel.getHandleResponseLayout().observe(getViewLifecycleOwner(), responseSuccess -> {
            if (responseSuccess) {
                awaitResponseUI();
                return;
            }
            responseArrivedUI();
        });
    }

    private void goLoginScreen() {
        if (getView() == null) return;

        Navigation.findNavController(getView())
                .navigate(R.id.action_signUpFragment_to_loginFragment);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        new DatePickerDialog(getContext(), this, year, month, day).show();
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

    private void awaitResponseUI() {
        mButtonSignUp.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void responseArrivedUI() {
        mButtonSignUp.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_back) {
            backScreen(v);
        } else if (id == R.id.card_date || id == R.id.button_calendar) {
            showDatePicker();
        } else if (id == R.id.button_eye) {
            changePasswordVisibility();
        } else if (id == R.id.button_sign) {
            String email = mEditEmail.getText().toString();
            String password = mEditPassword.getText().toString();
            String dateBirth = mTextDate.getText().toString();
            String name = mEditName.getText().toString();

            mViewModel.setUpUIToWaitResponse();
            mViewModel.createAccount(email, password, dateBirth, name);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mTextDate.setText(DateUtil.getDateFormatted(dayOfMonth, month, year));
    }
}
