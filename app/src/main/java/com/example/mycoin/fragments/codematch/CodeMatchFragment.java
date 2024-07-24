package com.example.mycoin.fragments.codematch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentCodeMatchBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;

public class CodeMatchFragment extends BaseFragment implements View.OnClickListener {

    private FragmentCodeMatchBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private CodeMatchViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCodeMatchBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = getViewModel(CodeMatchViewModel.class);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        mMenuNavigation = mBinding.navigationMenu;
    }

    private void initListeners() {
        mBinding.buttonBack.setOnClickListener(this);
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewRanking.setOnClickListener(this);
        mMenuNavigation.viewGoals.setOnClickListener(this);
    }

    private void goGoalsScreen(View v) {
    }

    private void goHomeScreen(View v) {
    }

    private void goRankingScreen(View v) {
    }

    private void goEditProfileScreen(View v) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileScreen(v);
        } else if (v.getId() == R.id.view_ranking) {
            goRankingScreen(v);
        } else if (v.getId() == R.id.view_home) {
            goHomeScreen(v);
        } else if (v.getId() == R.id.view_goals) {
            goGoalsScreen(v);
        }
    }

}