package com.example.mycoin.fragments.profile.editprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.entities.User;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.signup.SignUpFragment;
import com.example.mycoin.utils.LogcatUtil;

public class EditUserProfileFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(EditUserProfileFragment.class);

    private EditText mEditName;
    private EditText mEditEmail;
    private TextView mTextDateBirth;
    private Button mButtonEditProfile, mButtonBack;
    private EditUserProfileViewModel mViewModel;

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
        initObservers();
    }

    private void initComponents(View view) {
        mViewModel = getViewModel(EditUserProfileViewModel.class);
        mEditName = view.findViewById(R.id.edit_profile_name);
        mEditEmail = view.findViewById(R.id.edit_profile_email);
        mTextDateBirth = view.findViewById(R.id.text_date);
        mButtonEditProfile = view.findViewById(R.id.button_edit_profile);
        mButtonBack = view.findViewById(R.id.button_back);
    }

    private void loadUserData() {
        mViewModel.loadUserData();
    }

    private void initObservers() {
        mViewModel.getUserDataLoaded().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {

                Log.d(TAG, user.getEmail());
                Log.d(TAG, user.getName());
                Log.d(TAG, user.getBirthDate());
                mEditEmail.setText(user.getEmail());
                mEditName.setText(user.getName());
                mTextDateBirth.setText(user.getBirthDate());
            }
        });
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.button_edit_profile) {
//
//        }
    }
}