package com.example.mycoin.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(HomeFragment.class);

    private View mViewTarget, mViewHome, mViewProfile, mViewRanking;
    private View  mMenuNavigation;
    private HomeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(HomeViewModel.class);

        initComponents(view);
        initListeners();
        setCurrentUserData();
    }

    private void initComponents(View view) {
        mMenuNavigation = view.findViewById(R.id.navigation_menu);
        mViewTarget = mMenuNavigation.findViewById(R.id.view_target);
        mViewHome = mMenuNavigation.findViewById(R.id.view_home);
        mViewProfile = mMenuNavigation.findViewById(R.id.view_person);
        mViewRanking = mMenuNavigation.findViewById(R.id.view_ranking);
    }

    private void initListeners() {
    mViewProfile.setOnClickListener(this);
    }

    private void setCurrentUserData() {
        mViewModel.setCurrentUser();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_person) {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_generalProfileFragment);
        }
    }
}