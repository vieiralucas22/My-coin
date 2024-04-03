package com.example.mycoin.entities;

import java.util.Date;

public class User {

    private String mUsername;
    private String mEmail;
    private Date mBirthDate;
    private String mPassword;

    public User(String username, String email, Date birthDate, String password) {
        mUsername = username;
        mEmail = email;
        mBirthDate = birthDate;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public String getPassword() {
        return mPassword;
    }
}
