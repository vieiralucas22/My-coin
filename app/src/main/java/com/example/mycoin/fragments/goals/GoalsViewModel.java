package com.example.mycoin.fragments.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.LoadGoalsCallback;
import com.example.mycoin.gateway.repository.GoalRepository;
import com.example.mycoin.utils.LogcatUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GoalsViewModel extends ViewModel {
    private static final String TAG = LogcatUtil.getTag(GoalsViewModel.class);

    private GoalRepository mGoalRepository;
    private List<GoalsAdapter.GoalItem> mListGoals;
    private MutableLiveData<List<GoalsAdapter.GoalItem>> mLoadData = new MutableLiveData<>();

    @Inject
    public GoalsViewModel(GoalRepository goalRepository) {
        mGoalRepository = goalRepository;
    }

    public LiveData<List<GoalsAdapter.GoalItem>> getLoadData() {
        return mLoadData;
    }

    public List<GoalsAdapter.GoalItem> getGoalsList() {
        return mListGoals;
    }

    public void loadGoals() {
        mListGoals = new ArrayList<>();

        mGoalRepository.getAllGoals(new LoadGoalsCallback() {
            @Override
            public void onSuccess(List<GoalsAdapter.GoalItem> list) {
                mListGoals = list;
                mLoadData.postValue(list);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void loadGoalsCompleted() {
        List<GoalsAdapter.GoalItem> goalsCompleted = new ArrayList<>();

        for (GoalsAdapter.GoalItem goal : mListGoals) {
            if (goal.isDone()) {
                goalsCompleted.add(goal);
            }
        }

        mLoadData.postValue(goalsCompleted);
    }

    public void loadGoalsInProgress() {
        List<GoalsAdapter.GoalItem> goalsInProgress = new ArrayList<>();

        for (GoalsAdapter.GoalItem goal : mListGoals) {
            if (!goal.isDone()) {
                goalsInProgress.add(goal);
            }
        }

        mLoadData.postValue(goalsInProgress);
    }
}