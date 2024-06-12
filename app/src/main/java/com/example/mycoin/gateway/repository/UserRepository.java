package com.example.mycoin.gateway.repository;

import com.example.mycoin.entities.User;

import java.util.List;

public interface UserRepository {
    void insertUser();
    User getUser(int id);
    List<User> getAllUsers();
    void removeUser(int id);
    void updateCurrentUser(User user);
}
