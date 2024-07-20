package com.example.mycoin.repository;

import com.example.mycoin.callbacks.GoalCallback;
import com.example.mycoin.callbacks.LoadGoalsCallback;
import com.example.mycoin.gateway.repository.GoalRepository;
import com.example.mycoin.gateway.services.FirebaseService;

import javax.inject.Inject;

public class GoalRepositoryImpl implements GoalRepository {

    private FirebaseService mFirebaseService;

    @Inject
    public GoalRepositoryImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void getAllGoals(LoadGoalsCallback loadGoalsCallback) {
        mFirebaseService.getAllGoals(loadGoalsCallback);
    }

    @Override
    public void updateGoalAsCompleted(String goal) {
        mFirebaseService.completeUserGoal(goal);
    }

    @Override
    public void getGoal(String goal, GoalCallback goalCallback) {
        mFirebaseService.getGoal(goal, goalCallback);
    }
}
