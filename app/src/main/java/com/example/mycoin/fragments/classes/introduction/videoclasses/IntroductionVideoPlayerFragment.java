package com.example.mycoin.fragments.classes.introduction.videoclasses;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.mycoin.databinding.FragmentIntroductionVideoPlayerBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.home.HomeFragment;
import com.example.mycoin.utils.LogcatUtil;

public class IntroductionVideoPlayerFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LogcatUtil.getTag(IntroductionVideoPlayerFragment.class);
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mView;
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
        initObservers();
        mViewModel.sendClassForServer();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initObservers() {
        mViewModel.getServerResponse().observe(getViewLifecycleOwner(), response -> {
            mProgressBar.setVisibility(View.GONE);
            mView.setVisibility(View.INVISIBLE);
            mWebView.setVisibility(View.VISIBLE);

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadData(response, "text/html", "utf-8");
        });
    }


    private void initComponents() {
        mViewModel = getViewModel(IntroductionVideoPlayerViewModel.class);
        mWebView = mBinding.videoView;
        mView = mBinding.view2;
        mProgressBar = mBinding.progressBar;
    }

    @Override
    public void onClick(View v) {

    }
}