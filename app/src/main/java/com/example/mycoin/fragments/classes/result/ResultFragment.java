package com.example.mycoin.fragments.classes.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentResultBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class ResultFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(ResultFragment.class);

    private ImageView mImageSmile, mImageSad;
    private TextView mTextQuestionsRight, mTextQuestionsWrong, mTextPoints;
    private FragmentResultBinding mBinding;
    private ResultViewModel mViewModel;
    private NavigationMenuBinding mMenuNavigation;
    private View mViewTarget, mViewHome, mViewProfile, mViewRanking;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentResultBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(ResultViewModel.class);
        initComponents();
        initListeners();
        setUpUI();
    }

    private void initComponents() {
        mImageSad = mBinding.imageSad;
        mImageSmile = mBinding.imageSmile;
        mTextQuestionsRight = mBinding.textNumberRight;
        mTextQuestionsWrong = mBinding.textNumberWrong;
        mTextPoints = mBinding.textPoints;
        mMenuNavigation = mBinding.navigationMenu;
        mViewTarget = mMenuNavigation.viewTarget;
        mViewHome = mMenuNavigation.viewHome;
        mViewProfile = mMenuNavigation.viewPerson;
        mViewRanking = mMenuNavigation.viewRanking;
    }

    private ResultFragmentArgs getArgs() {
        if (getArguments() == null) return null;

        return ResultFragmentArgs.fromBundle(getArguments());
    }

    private void initListeners() {
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
    }

    private void setUpUI() {
        if (getArgs() == null) return;

        mTextQuestionsRight.setText(String.valueOf(getArgs().getTotalRightQuestions()));
        mTextQuestionsWrong.setText(String.valueOf(getArgs().getTotalWrongQuestions()));
        mTextPoints.setText(String.valueOf(getArgs().getTotalPoints()));
    }

    private void goEditProfileTab(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_resultFragment_to_generalProfileFragment);
    }

    private void goHomeTab(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_resultFragment_to_homeFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_home) {
            goHomeTab(v);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileTab(v);
        }
    }
}