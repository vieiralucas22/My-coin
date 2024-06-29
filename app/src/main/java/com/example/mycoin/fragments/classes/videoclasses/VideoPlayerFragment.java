package com.example.mycoin.fragments.classes.videoclasses;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentIntroductionVideoPlayerBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

public class VideoPlayerFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LogcatUtil.getTag(VideoPlayerFragment.class);
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mButtonQuiz;
    private View mView;
    private FragmentIntroductionVideoPlayerBinding mBinding;

    private VideoPlayerViewModel mViewModel;

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
        initListeners();
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
        mViewModel = getViewModel(VideoPlayerViewModel.class);
        mWebView = mBinding.videoView;
        mView = mBinding.view2;
        mProgressBar = mBinding.progressBar;
        mButtonQuiz = mBinding.buttonQuiz;
    }

    private void initListeners() {
        mButtonQuiz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_quiz) {
            Navigation.findNavController(v).navigate(R.id.action_introductionVideoPlayerFragment_to_quizFragment);
        }
    }
}