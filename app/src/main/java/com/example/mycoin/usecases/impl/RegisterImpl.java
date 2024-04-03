package com.example.mycoin.usecases.impl;

import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.LogcatUtil;

public class RegisterImpl implements Register {

    private static final String TAG = LogcatUtil.getTag(RegisterImpl.class);
    @Override
    public boolean signUp(String email, String password, String dateBirth, String username) {
        return false;
    }
}
