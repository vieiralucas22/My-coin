package com.example.mycoin.entities;

import java.util.Date;

public class User {

    private String mName;
    private String mEmail;
    private String mBirthDate;
    private String mPassword;

    public User(String name, String email, String birthDate, String password) {
        mName = name;
        mEmail = email;
        mBirthDate = birthDate;
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getBirthDate() {
        return mBirthDate;
    }

    public String getPassword() {
        return mPassword;
    }
}
