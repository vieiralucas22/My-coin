package com.example.mycoin.callbacks;

import com.example.mycoin.fragments.goals.GoalsAdapter;

import java.util.List;

public interface LoadGoalsCallback {
    void onSuccess(List<GoalsAdapter.GoalItem> list);
    void onFailure(String message);
}
