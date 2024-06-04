package com.example.mycoin.fragments.classes.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentResultBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class ResultFragment extends BaseFragment {
    public static final String TAG = LogcatUtil.getTag(ResultFragment.class);

    private ImageView mImageSmile, mImageSad;
    private TextView mTextQuestionsRight, mTextQuestionsWrong, mTextPoints;
    private FragmentResultBinding mBinding;
    private ResultViewModel mViewModel;

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
        setUpUI();
    }

    private void initComponents() {
        mImageSad = mBinding.imageSad;
        mImageSmile = mBinding.imageSmile;
        mTextQuestionsRight = mBinding.textNumberRight;
        mTextQuestionsWrong = mBinding.textNumberWrong;
        mTextPoints = mBinding.textPoints;
    }

    private ResultFragmentArgs getArgs() {
        if (getArguments() == null) return null;

        return ResultFragmentArgs.fromBundle(getArguments());
    }

    private void setUpUI() {
        if (getArgs() == null) return;

        mTextQuestionsRight.setText(String.valueOf(getArgs().getTotalRightQuestions()));
        mTextQuestionsWrong.setText(String.valueOf(getArgs().getTotalWrongQuestions()));
        mTextPoints.setText(String.valueOf(getArgs().getTotalPoints()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
}