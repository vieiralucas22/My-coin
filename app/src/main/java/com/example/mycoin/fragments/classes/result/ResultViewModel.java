package com.example.mycoin.fragments.classes.result;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.preferences.AppPreferences;

import javax.inject.Inject;

public class ResultViewModel extends ViewModel {

    private AppPreferences mAppPreferences;
    private UserRepository mUserRepository;

    @Inject
    public ResultViewModel(AppPreferences appPreferences, UserRepository userRepository) {
        mAppPreferences = appPreferences;
        mUserRepository = userRepository;
    }

    public void saveUserPoints(int pointsGained) {
        User user = mAppPreferences.getCurrentUser();
        int newUserPoints = user.getPoints() + pointsGained;
        user.setPoints(newUserPoints);
        mUserRepository.updateCurrentUser(user);
    }
}