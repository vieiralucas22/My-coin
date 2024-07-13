package com.example.mycoin.fragments.classes.videoclasses;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentVideoPlayerBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.ListUtil;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class VideoPlayerFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LogcatUtil.getTag(VideoPlayerFragment.class);
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mButtonQuiz;
    private View mView;
    private FragmentVideoPlayerBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;
    private ClassAdapter mAdapter;
    private String mModule;

    private VideoPlayerViewModel mViewModel;

    @Inject
    ClassRepository classRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentVideoPlayerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "Entered in video player fragment");

        DaggerAppComponent.builder()
                .applicationModule(new AppModule(getContext()))
                .build()
                .inject(this);

        mViewModel = getViewModel(VideoPlayerViewModel.class);

        initComponents();
        initObservers();
        mViewModel.sendClassForServer();
        initListeners();
    }

    private void setUpNextClasses() {
        if (getArgs() == null) return;

        int classPosition = getArgs().getClassPosition();
        mModule = getArgs().getModule();

        mViewModel.showNextClasses(classPosition, mModule);
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

        mViewModel.getLoadNextClasses().observe(getViewLifecycleOwner(), this::showNextClasses);
    }

    private void showNextClasses(Boolean isLoaded) {
        if (isLoaded && !ListUtil.isEmpty(mViewModel.getNextClasses())) {
            mAdapter.setItems(mViewModel.getNextClasses());
            return;
        }
        mBinding.noClasses.setVisibility(View.VISIBLE);
        mBinding.smile.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.INVISIBLE);
    }

    private void initComponents() {
        setUpNextClasses();
        mMenuNavigation = mBinding.navigationMenu;
        mWebView = mBinding.videoView;
        mView = mBinding.view2;
        mProgressBar = mBinding.progressBar;
        mButtonQuiz = mBinding.buttonQuiz;
        mAdapter = new ClassAdapter(mViewModel.getNextClasses(),
                classRepository, mModule, false);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void initListeners() {
        mButtonQuiz.setOnClickListener(this);
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewRanking.setOnClickListener(this);
    }

    private void goRankingScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_videoPlayerFragment_to_rankingFragment);
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_videoPlayerFragment_to_homeFragment);
    }

    private void goEditProfileScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_videoPlayerFragment_to_generalProfileFragment);
    }

    private VideoPlayerFragmentArgs getArgs() {
        if (getArguments() == null) return null;

        return VideoPlayerFragmentArgs.fromBundle(getArguments());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_quiz) {
            Navigation.findNavController(v)
                    .navigate(R.id.action_introductionVideoPlayerFragment_to_quizFragment);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileScreen(v);
        } else if (v.getId() == R.id.view_ranking) {
            goRankingScreen(v);
        } else if (v.getId() == R.id.view_home) {
            goHomeScreen(v);
        }
    }
}