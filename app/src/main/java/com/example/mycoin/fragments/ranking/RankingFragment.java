package com.example.mycoin.fragments.ranking;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentRankingBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;

import com.example.mycoin.utils.ListUtil;
import com.example.mycoin.utils.LogcatUtil;
import com.squareup.picasso.Picasso;

public class RankingFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(RankingFragment.class);

    private RankingListAdapter mAdapter;
    private FragmentRankingBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private RankingViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRankingBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "Entered in ranking fragment");

        mViewModel = getViewModel(RankingViewModel.class);

        mMenuNavigation = mBinding.navigationMenu;
        initUI();
        initListeners();
        initObservers();
    }

    private void initUI() {
        mViewModel.loadAllPlayer();
        mAdapter = new RankingListAdapter(mViewModel.getRankingItems());
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void initListeners() {
        mBinding.buttonBack.setOnClickListener(this);
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewGoals.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getUsersLoaded().observe(getViewLifecycleOwner(), this::showUsersInRanking);
    }

    private void showUsersInRanking(Boolean isLoad) {
        if (isLoad && !ListUtil.isEmpty(mViewModel.getRankingItems())) {
            mAdapter.setItems(mViewModel.getRankingItems());
            if (mViewModel.getRankingItems().size() < 3) return;
            Picasso.get().load(Uri.parse(mViewModel.getRankingItems().get(0).getPlayerPhoto())).into(mBinding.userFirst);
            Picasso.get().load(Uri.parse(mViewModel.getRankingItems().get(1).getPlayerPhoto())).into(mBinding.userSecond);
            Picasso.get().load(Uri.parse(mViewModel.getRankingItems().get(2).getPlayerPhoto())).into(mBinding.userThird);
        }
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_rankingFragment_to_homeFragment);
    }

    private void goEditProfileScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_rankingFragment_to_generalProfileFragment);
    }

    private void goGoalsScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_rankingFragment_to_goalsFragment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileScreen(v);
        } else if (v.getId() == R.id.view_home) {
            goHomeScreen(v);
        } else if (v.getId() == R.id.view_goals) {
            goGoalsScreen(v);
        }
    }
}
