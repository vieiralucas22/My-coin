package com.example.mycoin.fragments.profile.generalprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class GeneralProfileFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(GeneralProfileFragment.class);

    private Button mButtonBack;
    private ViewGroup mEditUserProfileArea, mChangePasswordArea, mLogoutArea;
    private GeneralProfileViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(GeneralProfileViewModel.class);

        Log.d(TAG, "Enter in user profile fragment");

        initComponents(view);
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mButtonBack = view.findViewById(R.id.button_back);
        mEditUserProfileArea = view.findViewById(R.id.edit_profile_area);
        mChangePasswordArea = view.findViewById(R.id.change_password_area);
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mEditUserProfileArea.setOnClickListener(this);
        mChangePasswordArea.setOnClickListener(this);
    }

    private void initObservers() {
    }

    private void goEditProfileScreen() {
        if (getView() == null) return;
        Navigation.findNavController(getView())
                .navigate(R.id.action_generalProfileFragment_to_editUserProfileFragment);
    }

    private void goChangePasswordScreen() {
        if (getView() == null) return;
        Navigation.findNavController(getView())
                .navigate(R.id.action_generalProfileFragment_to_changeUserPasswordFragment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.edit_profile_area) {
            goEditProfileScreen();
        } else if (v.getId() == R.id.change_password_area) {
            goChangePasswordScreen();
        }
    }
}