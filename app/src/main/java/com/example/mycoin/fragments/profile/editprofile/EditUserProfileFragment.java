package com.example.mycoin.fragments.profile.editprofile;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.entities.User;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.signup.SignUpFragment;
import com.example.mycoin.utils.DateUtil;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import java.util.Calendar;

public class EditUserProfileFragment extends BaseFragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {
    public static final String TAG = LogcatUtil.getTag(EditUserProfileFragment.class);

    private EditText mEditName;
    private EditText mEditEmail;
    private TextView mTextDateBirth;
    private Button mButtonEditProfile, mButtonBack, mButtonDatePicker;
    private CardView mCardDatePicker;
    private EditUserProfileViewModel mViewModel;

    private String userBirth;

    public static EditUserProfileFragment newInstance() {
        return new EditUserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in edit profile fragment");

        initComponents(view);
        loadUserData();
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mViewModel = getViewModel(EditUserProfileViewModel.class);
        mEditName = view.findViewById(R.id.edit_profile_name);
        mEditEmail = view.findViewById(R.id.edit_profile_email);
        mTextDateBirth = view.findViewById(R.id.text_date);
        mButtonEditProfile = view.findViewById(R.id.button_edit_profile);
        mButtonBack = view.findViewById(R.id.button_back);
        mCardDatePicker = view.findViewById(R.id.edit_profile_date_birth_card);
        mButtonDatePicker = view.findViewById(R.id.button_calendar);
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mCardDatePicker.setOnClickListener(this);
        mButtonDatePicker.setOnClickListener(this);
    }

    private void loadUserData() {
        mViewModel.loadUserData();
    }

    private void initObservers() {
        mViewModel.getUserDataLoaded().observe(getViewLifecycleOwner(), user -> {

            if (user != null) {
                userBirth = user.getBirthDate();

                mEditEmail.setText(user.getEmail());
                mEditName.setText(user.getName());
                mTextDateBirth.setText(userBirth);
            }
        });
    }

    private void showDatePicker() {
        int day, month, year;

        if (!TextUtils.isEmpty(userBirth)) {
            day = DateUtil.getDayByFormattedDate(userBirth);
            month = DateUtil.getMonthByFormattedDate(userBirth);
            year = DateUtil.getYearByFormattedDate(userBirth);
        } else {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }

        new DatePickerDialog(getContext(), this, year, month, day).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_edit_profile) {

        } else if (id == R.id.edit_profile_date_birth_card || id == R.id.button_calendar) {
            showDatePicker();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mTextDateBirth.setText(DateUtil.getDateFormatted(dayOfMonth, month, year));

    }
}