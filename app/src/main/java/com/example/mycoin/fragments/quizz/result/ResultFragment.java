package com.example.mycoin.fragments.quizz.result;

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

    private int mPoints = 0;

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
        mViewModel.saveUserPoints(mPoints);
    }

    private void initComponents() {
        mImageSad = mBinding.imageSad;
        mImageSmile = mBinding.imageSmile;
        mTextQuestionsRight = mBinding.textNumberRight;
        mTextQuestionsWrong = mBinding.textNumberWrong;
        mTextPoints = mBinding.textPoints;
        mMenuNavigation = mBinding.navigationMenu;
    }

    private ResultFragmentArgs getArgs() {
        if (getArguments() == null) return null;

        return ResultFragmentArgs.fromBundle(getArguments());
    }

    private void initListeners() {
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewRanking.setOnClickListener(this);
    }

    private void setUpUI() {
        if (getArgs() == null) return;

        mPoints = getArgs().getTotalPoints();

        int rightQuestions = getArgs().getTotalRightQuestions();
        int wrongQuestions = getArgs().getTotalWrongQuestions();

        mTextQuestionsRight.setText(String.valueOf(rightQuestions));
        mTextQuestionsWrong.setText(String.valueOf(wrongQuestions));
        mTextPoints.setText(String.valueOf(mPoints));

        if (rightQuestions > wrongQuestions) {
            mImageSad.setVisibility(View.INVISIBLE);
            mImageSmile.setVisibility(View.VISIBLE);
        } else {
            mImageSad.setVisibility(View.VISIBLE);
            mImageSmile.setVisibility(View.INVISIBLE);
        }

    }

    private void goEditProfileTab(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_resultFragment_to_generalProfileFragment);
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_resultFragment_to_homeFragment);
    }

    private void goRankingScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_resultFragment_to_rankingFragment);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_home) {
            goHomeScreen(v);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileTab(v);
        } else if (v.getId() == R.id.view_ranking) {
            goRankingScreen(v);
        }
    }
}