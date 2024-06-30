package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.LoadUsersCallback;
import com.example.mycoin.entities.User;

public interface UserRepository {
    void insertUser();
    User getUser(int id);
    void getAllUsers(LoadUsersCallback loadUsersCallback);
    void removeUser(int id);
    void updateCurrentUser(User user);
}
