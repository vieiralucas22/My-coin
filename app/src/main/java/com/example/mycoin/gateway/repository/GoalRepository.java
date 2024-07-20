package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.GoalCallback;
import com.example.mycoin.callbacks.LoadGoalsCallback;

public interface GoalRepository {
    void getAllGoals(LoadGoalsCallback loadGoalsCallback);
    void updateGoalAsCompleted(String goal);
    void getGoal(String goal, GoalCallback goalCallback);
}
