package com.example.mycoin.repository;

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
}
