package com.example.mycoin.fragments.signup;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.example.mycoin.utils.Logger;

import java.util.Calendar;

public class SignUpFragment extends BaseFragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    public static final String TAG = SignUpFragment.class.getSimpleName();

    private Button mButtonBack, mButtonDatePicker, mButtonEye;
    private TextView mTextDate;
    private CardView mCardDatePicker;
    private EditText mEditPassword;

    private boolean mIsPasswordVisible = false;

    private SignUpViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(TAG, "Enter in sign up fragment");

        initComponents(view);
    }

    private void initComponents(View view) {
        mButtonBack = view.findViewById(R.id.button_back);
        mCardDatePicker = view.findViewById(R.id.card_date);
        mTextDate = view.findViewById(R.id.text_date);
        mButtonDatePicker = view.findViewById(R.id.button_calendar);
        mButtonEye = view.findViewById(R.id.button_eye);
        mEditPassword = view.findViewById(R.id.edit_password);

        initListeners();
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mCardDatePicker.setOnClickListener(this);
        mButtonDatePicker.setOnClickListener(this);
        mButtonEye.setOnClickListener(this);
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
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mTextDate.setText(DateUtil.getDateFormatted(dayOfMonth, month, year));
    }
}
