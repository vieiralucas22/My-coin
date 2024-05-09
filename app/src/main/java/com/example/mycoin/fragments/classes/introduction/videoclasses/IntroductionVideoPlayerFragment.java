package com.example.mycoin.fragments.classes.introduction.videoclasses;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentConfirmCodeBinding;
import com.example.mycoin.databinding.FragmentIntroductionVideoPlayerBinding;
import com.example.mycoin.fragments.BaseFragment;

public class IntroductionVideoPlayerFragment extends BaseFragment implements View.OnClickListener {
    private VideoView mVideoView;
    private FragmentIntroductionVideoPlayerBinding mBinding;

    private IntroductionVideoPlayerViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentIntroductionVideoPlayerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        mVideoView = mBinding.videoView;
    }

    @Override
    public void onClick(View v) {

    }
}