package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.LoadGoalsCallback;

public interface GoalRepository {
    void getAllGoals(LoadGoalsCallback loadGoalsCallback);
}
