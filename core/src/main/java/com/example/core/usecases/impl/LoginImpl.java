package com.example.core.usecases.impl;

import com.example.core.usecases.interfaces.Login;

public class LoginImpl implements Login {
    @Override
    public boolean authenticate(String email, String password) {
        return false;
    }
}
