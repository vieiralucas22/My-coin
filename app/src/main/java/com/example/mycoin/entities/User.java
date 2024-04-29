package com.example.mycoin.entities;

public class User {

    private String mName;
    private String mEmail;
    private String mBirthDate;
    private String mPassword;

    public User() {
    }

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

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setBirthDate(String mBirthDate) {
        this.mBirthDate = mBirthDate;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
