package com.example.mycoin.repository;

import com.example.mycoin.callbacks.LoadUsersCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {
    public static final String TAG = LogcatUtil.getTag(UserRepositoryImpl.class);
    private final FirebaseService mFirebaseService;

    @Inject
    public UserRepositoryImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void insertUser() {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public void getAllUsers(LoadUsersCallback loadUsersCallback) {
        mFirebaseService.getAllUsers(loadUsersCallback);
    }

    @Override
    public void removeUser(int id) {

    }

    @Override
    public void updateCurrentUser(User user) {
        mFirebaseService.updateUser(user);
    }
}
