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
        initComponents();
        getArgs();
    }

    private void initComponents() {
        mImageSad = mBinding.imageSad;
        mImageSmile = mBinding.imageSmile;
        mTextQuestionsRight = mBinding.textNumberRight;
        mTextQuestionsWrong = mBinding.textNumberWrong;
        mTextPoints = mBinding.textPoints;
    }

    private void getArgs() {
        if (getArguments() == null) return;

        ResultFragmentArgs args = ResultFragmentArgs.fromBundle(getArguments());
        mTextQuestionsRight.setText(String.valueOf(args.getTotalRightQuestions()));
        mTextQuestionsWrong.setText(String.valueOf(args.getTotalWrongQuestions()));
        mTextPoints.setText(String.valueOf(args.getTotalPoints()));
    }
}