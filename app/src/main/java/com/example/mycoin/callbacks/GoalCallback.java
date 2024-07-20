package com.example.mycoin.callbacks;

import com.example.mycoin.entities.Goal;

public interface GoalCallback {
    void onSuccess(Goal goal);
    void onFailure();
}
