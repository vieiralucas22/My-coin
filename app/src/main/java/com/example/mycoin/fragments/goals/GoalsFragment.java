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
import com.example.mycoin.utils.ListUtil;
import com.example.mycoin.utils.LogcatUtil;

public class GoalsFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = LogcatUtil.getTag(GoalsFragment.class);

    private FragmentGoalsBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private GoalsViewModel mViewModel;
    private GoalsAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGoalsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(GoalsViewModel.class);

        initComponents();
        initListeners();
        initObservers();
        mViewModel.loadGoals();
        mAdapter = new GoalsAdapter(mViewModel.getGoalsList());
        mBinding.recyclerView.setAdapter(mAdapter);
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

    private void initObservers() {
        mViewModel.getLoadData().observe(getViewLifecycleOwner(), this::showGoals);
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

    private void showGoals(Boolean isLoad) {
        if (isLoad && !ListUtil.isEmpty(mViewModel.getGoalsList())) {
            mAdapter.setItems(mViewModel.getGoalsList());
        }
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