package com.example.core.entities;

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

    public User() {
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date mBirthDate) {
        this.mBirthDate = mBirthDate;
    }

    public String getPassword() {
        return mPassword;
    }
}
