package com.example.mycoin.fragments.home;

import static com.example.mycoin.constants.Constants.INTRODUCTION;
import static com.example.mycoin.constants.Constants.ORGANIZE_HOME;
import static com.example.mycoin.constants.Constants.ACTION_TIME;
import static com.example.mycoin.constants.Constants.EXTRA;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    private RelativeLayout mCardIntroduction;
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
        Log.i(TAG, "Entered in home fragment");

        mViewModel = getViewModel(HomeViewModel.class);
        initComponents();
        initListeners();
        setCurrentUserData();
    }

    private void initComponents() {
        mMenuNavigation = mBinding.navigationMenu;
        mViewTarget = mMenuNavigation.viewTarget;
        mViewHome = mMenuNavigation.viewHome;
        mViewProfile = mMenuNavigation.viewPerson;
        mViewRanking = mMenuNavigation.viewRanking;
        mUserName = mBinding.userName;
        mCardIntroduction = mBinding.introductionCard;
    }

    private void initListeners() {
        mViewProfile.setOnClickListener(this);
        mViewRanking.setOnClickListener(this);
        mCardIntroduction.setOnClickListener(this);
        mBinding.cardManageFinancial.setOnClickListener(this);
        mBinding.cardTargetGoals.setOnClickListener(this);
        mBinding.cardExtra.setOnClickListener(this);
    }

    private void setCurrentUserData() {
        mViewModel.setCurrentUser();
        mUserName.setText(mViewModel.getUserName());
        mBinding.userPoints.setText(mViewModel.getUserPoints());
    }

    private void goEditProfileTab(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_homeFragment_to_generalProfileFragment);
    }

    private void goIntroductionModule(View v) {
        NavDirections action = HomeFragmentDirections
                .actionHomeFragmentToIntroductionClassesFragment().setModule(INTRODUCTION);

        Navigation.findNavController(v).navigate(action);
    }

    private void goOrganizeHomeModule(View v) {
        NavDirections action = HomeFragmentDirections
                .actionHomeFragmentToIntroductionClassesFragment().setModule(ORGANIZE_HOME);

        Navigation.findNavController(v).navigate(action);
    }

    private void goActionTimeModule(View v) {
        NavDirections action = HomeFragmentDirections
                .actionHomeFragmentToIntroductionClassesFragment().setModule(ACTION_TIME);

        Navigation.findNavController(v).navigate(action);
    }

    private void goExtraModule(View v) {
        NavDirections action = HomeFragmentDirections
                .actionHomeFragmentToIntroductionClassesFragment().setModule(EXTRA);

        Navigation.findNavController(v).navigate(action);
    }

    private void goRankingScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_rankingFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_person) {
            goEditProfileTab(v);
        } else if (v.getId() == R.id.introduction_card) {
            goIntroductionModule(v);
        } else if (v.getId() == R.id.card_manage_financial) {
            goOrganizeHomeModule(v);
        } else if (v.getId() == R.id.card_target_goals) {
            goActionTimeModule(v);
        } else if (v.getId() == R.id.card_extra) {
            goExtraModule(v);
        } else if (v.getId() == R.id.view_ranking) {
            goRankingScreen(v);
        }
    }
}