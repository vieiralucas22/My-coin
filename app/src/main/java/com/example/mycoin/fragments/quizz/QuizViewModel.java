package com.example.mycoin.fragments.quizz;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.preferences.AppPreferences;

import javax.inject.Inject;

public class QuizViewModel extends ViewModel {

    private final AppPreferences mAppPreferences;
    private final UserRepository mUserRepository;

    @Inject
    public QuizViewModel(AppPreferences appPreferences, UserRepository userRepository) {
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
