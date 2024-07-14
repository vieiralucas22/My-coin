package com.example.mycoin.fragments.goals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentGoalsBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;

public class GoalsFragment extends BaseFragment implements View.OnClickListener {

    private FragmentGoalsBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private GoalsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGoalsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }

    private void goRankingScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_goalsFragment_to_rankingFragment);
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_goalsFragment_to_homeFragment);
    }

    private void goEditProfileScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_goalsFragment_to_generalProfileFragment);
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
        }
    }
}