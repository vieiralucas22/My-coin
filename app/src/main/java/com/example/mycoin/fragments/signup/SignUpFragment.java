package com.example.mycoin.fragments.signup;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;

import com.example.mycoin.R;
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

    private boolean mIsPasswordVisible = false;

    private SignUpViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in sign up fragment");

        initComponents(view);
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mViewModel = getViewModel(SignUpViewModel.class);
        mButtonBack = view.findViewById(R.id.button_back);
        mCardDatePicker = view.findViewById(R.id.card_date);
        mTextDate = view.findViewById(R.id.text_date);
        mButtonDatePicker = view.findViewById(R.id.button_calendar);
        mButtonEye = view.findViewById(R.id.button_eye);
        mEditPassword = view.findViewById(R.id.edit_password);
        mButtonSignUp = view.findViewById(R.id.button_sign);
        mEditEmail = view.findViewById(R.id.edit_email_sign_up);
        mEditName = view.findViewById(R.id.edit_username);
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
            }
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
            Log.d(TAG, dateBirth);

            mViewModel.createAccount(email, password, dateBirth, name);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mTextDate.setText(DateUtil.getDateFormatted(dayOfMonth, month, year));
    }
}
