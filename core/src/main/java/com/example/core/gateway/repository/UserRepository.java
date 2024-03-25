package com.example.core.gateway.repository;

import com.example.core.entities.User;

import java.util.List;

public interface UserRepository {
    void insertUser();
    User getUser(int id);
    List<User> getAllUsers();
    void removeUser(int id);
}
