package com.example.mycoin.fragments.profile.editprofile;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.text.TextUtils;
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
import com.example.mycoin.databinding.FragmentEditUserProfileBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.DateUtil;
import com.example.mycoin.utils.LogcatUtil;

import java.util.Calendar;

public class EditUserProfileFragment extends BaseFragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {
    public static final String TAG = LogcatUtil.getTag(EditUserProfileFragment.class);

    private EditText mEditName;
    private TextView mTextDateBirth;
    private Button mButtonEditProfile, mButtonBack, mButtonDatePicker;
    private CardView mCardDatePicker;
    private EditUserProfileViewModel mViewModel;
    private ProgressBar mProgressBar;
    private FragmentEditUserProfileBinding mBinding;

    private String userBirth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEditUserProfileBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in edit profile fragment");

        mViewModel = getViewModel(EditUserProfileViewModel.class);

        initComponents();
        loadUserData();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mEditName = mBinding.editProfileName;
        mTextDateBirth = mBinding.textDate;
        mButtonEditProfile = mBinding.buttonEditProfile;
        mButtonBack = mBinding.buttonBack;
        mCardDatePicker = mBinding.editProfileDateBirthCard;
        mButtonDatePicker = mBinding.buttonCalendar;
        mProgressBar = mBinding.progressBar;
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mCardDatePicker.setOnClickListener(this);
        mButtonDatePicker.setOnClickListener(this);
        mButtonEditProfile.setOnClickListener(this);
    }

    private void loadUserData() {
        mViewModel.loadUserData();
    }

    private void initObservers() {
        mViewModel.getUserDataLoaded().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                userBirth = user.getBirthDate();
                mEditName.setText(user.getName());
                mTextDateBirth.setText(userBirth);
            }
        });

        mViewModel.getHandleResponseLayout().observe(getViewLifecycleOwner(), isEdited -> {
            responseArrivedUI();
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

    private void waitResponseUI() {
        mButtonEditProfile.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void responseArrivedUI() {
        mButtonEditProfile.setVisibility(View.VISIBLE);
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
        if (id == R.id.button_edit_profile) {
            String name = mEditName.getText().toString();
            String dataBirth = mTextDateBirth.getText().toString();

            waitResponseUI();
            mViewModel.editUserData(name, dataBirth);
        } else if (id == R.id.edit_profile_date_birth_card || id == R.id.button_calendar) {
            showDatePicker();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mTextDateBirth.setText(DateUtil.getDateFormatted(dayOfMonth, month, year));
    }
}