package com.example.mycoin.fragments.goals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.databinding.FragmentGoalsBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.customview.skeleton.SkeletonAdapter;
import com.example.mycoin.utils.ListUtil;
import com.example.mycoin.utils.LogcatUtil;

import java.util.ArrayList;
import java.util.List;

public class GoalsFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = LogcatUtil.getTag(GoalsFragment.class);

    private FragmentGoalsBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private GoalsViewModel mViewModel;
    private GoalsAdapter mAdapter;
    private SkeletonAdapter mSkeletonAdapter;
    private List<Boolean> mSkeletonList;

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
        initSkeletonAdapter();
        mAdapter = new GoalsAdapter(mViewModel.getGoalsList(), getContext());
    }

    private void initComponents() {
        mMenuNavigation = mBinding.navigationMenu;
    }

    private void initListeners() {
        mBinding.buttonBack.setOnClickListener(this);
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewRanking.setOnClickListener(this);
        mMenuNavigation.viewControl.setOnClickListener(this);
        mBinding.buttonDone.setOnClickListener(this);
        mBinding.buttonInProgress.setOnClickListener(this);
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

    private void goCodeMatchScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_goalsFragment_to_codeMatchFragment);
    }

    private void showGoals(List<GoalsAdapter.GoalItem> goalList) {
        if (!ListUtil.isEmpty(goalList)) {
            mAdapter.setItems(goalList);
        }
    }

    private void initSkeletonAdapter() {
        mSkeletonList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mSkeletonList.add(true);
        }
        mSkeletonAdapter = new SkeletonAdapter(mSkeletonList, Constants.SKELETON_GOALS);
        mBinding.recyclerView.setAdapter(mSkeletonAdapter);

        mBinding.recyclerView.postDelayed(() -> {
            mBinding.recyclerView.setAdapter(mAdapter);
        }, 1000);
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
        } else if (v.getId() == R.id.view_control) {
            goCodeMatchScreen(v);
        } else if (v.getId() == R.id.button_done) {
            mViewModel.loadGoalsCompleted();
        } else if (v.getId() == R.id.button_in_progress) {
            mViewModel.loadGoalsInProgress();
        }
    }
}