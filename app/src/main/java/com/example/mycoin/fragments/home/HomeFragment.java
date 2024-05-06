package com.example.mycoin.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentHomeBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(HomeFragment.class);

    private View mViewTarget, mViewHome, mViewProfile, mViewRanking;
    private TextView mUserName;
    private NavigationMenuBinding mMenuNavigation;
    private FragmentHomeBinding mBinding;

    private HomeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(HomeViewModel.class);

        initComponents();
        initListeners();
        setCurrentUserData();
        initObservers();
    }

    private void initComponents() {
        mMenuNavigation = mBinding.navigationMenu;
        mViewTarget = mMenuNavigation.viewTarget;
        mViewHome = mMenuNavigation.viewHome;
        mViewProfile = mMenuNavigation.viewPerson;
        mViewRanking = mMenuNavigation.viewRanking;
        mUserName = mBinding.userName;
    }

    private void initListeners() {
    mViewProfile.setOnClickListener(this);
    }

    private void setCurrentUserData() {
        mViewModel.setCurrentUser();
        mUserName.setText(mViewModel.getUserName());
    }

    private void initObservers() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_person) {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_generalProfileFragment);
        }
    }
}