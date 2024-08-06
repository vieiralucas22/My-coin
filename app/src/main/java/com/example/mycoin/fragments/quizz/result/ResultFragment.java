package com.example.mycoin.fragments.quizz.result;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycoin.InternalIntents;
import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.databinding.FragmentResultBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.receivers.GoalCompletedReceiver;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

public class ResultFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(ResultFragment.class);

    private ImageView mImageSmile, mImageSad;
    private TextView mTextQuestionsRight, mTextQuestionsWrong, mTextPoints;
    private FragmentResultBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;
    private GoalCompletedReceiver mReceiver = new GoalCompletedReceiver();

    private ResultFragmentViewModel mViewModel;

    private int mPoints = 0;

    @Inject
    AppPreferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentResultBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(ResultFragmentViewModel.class);

        DaggerAppComponent.builder()
                .applicationModule(new AppModule(getContext()))
                .build()
                .inject(this);

        registerReceiver(mReceiver, new IntentFilter(InternalIntents.ACTION_GOAL_COMPLETED));

        initComponents();
        initListeners();
        initObservers();

        mViewModel.initFirebaseListener(getArgs().getRoomCode());

        setUpUI();
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
        mMenuNavigation.viewGoals.setOnClickListener(this);
    }

    private void setUpUI() {
        if (mViewModel.isOnlineMatch()) {
            setUpOnlineUI();
        } else {
            setUpOfflineUI();
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

    private void goGoalsScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_resultFragment_to_goalsFragment);
    }

    private void initObservers() {
        mViewModel.getIsGameDone().observe(getViewLifecycleOwner(), gameResult -> {
            mBinding.textResultMatch.setText(checkIfPlayerWin(mViewModel.getWinner(gameResult)) ? "Winner" : "Loser");
            mBinding.progressBar.setVisibility(View.INVISIBLE);
            mBinding.textWaitingDescription.setVisibility(View.INVISIBLE);
        });
    }

    private boolean checkIfPlayerWin(String winner) {
        return preferences.getCurrentUser().getEmail().equals(winner);
    }

    private void setUpOnlineUI() {
        mImageSmile.setVisibility(View.GONE);
        mImageSad.setVisibility(View.GONE);
        mBinding.textWaitingDescription.setVisibility(View.VISIBLE);
        mBinding.progressBar.setVisibility(View.VISIBLE);
        mBinding.textTitle.setText(getString(R.string.game_result));
        mBinding.textResultMatch.setVisibility(View.VISIBLE);
    }

    private void setUpOfflineUI() {
        if (getArgs() == null) return;

        mPoints = getArgs().getTotalPoints();

        int rightQuestions = getArgs().getTotalRightQuestions();
        int wrongQuestions = getArgs().getTotalWrongQuestions();

        mTextQuestionsRight.setText(String.valueOf(rightQuestions));
        mTextQuestionsWrong.setText(String.valueOf(wrongQuestions));
        mTextPoints.setText(String.valueOf(mPoints));
        mBinding.textTitle.setText(getString(R.string.quiz_result));

        if (rightQuestions >= wrongQuestions) {
            mImageSad.setVisibility(View.INVISIBLE);
            mImageSmile.setVisibility(View.VISIBLE);
            Intent intent = new Intent(InternalIntents.ACTION_GOAL_COMPLETED);
            intent.putExtra(Constants.GOAL_DONE, Constants.GOAL_FIRST_QUIZ);
            sendBroadcast(intent);
        } else {
            mImageSad.setVisibility(View.VISIBLE);
            mImageSmile.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_home) {
            goHomeScreen(v);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileTab(v);
        } else if (v.getId() == R.id.view_ranking) {
            goRankingScreen(v);
        } else if (v.getId() == R.id.view_goals) {
            goGoalsScreen(v);
        }
    }
}