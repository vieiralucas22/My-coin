package com.example.mycoin.entities;

public class User {

    private String mName;
    private String mEmail;
    private String mBirthDate;
    private String mPassword;
    private int mPoints = 0;

    public User() {
    }

    public User(String mName, String mEmail, String mBirthDate, String mPassword) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mBirthDate = mBirthDate;
        this.mPassword = mPassword;
    }

    public User(String name, String email, String birthDate, String password, int points) {
        mName = name;
        mEmail = email;
        mBirthDate = birthDate;
        mPassword = password;
        mPoints = points;
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

    public int getPoints() {
        return mPoints;
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

    public void setPoints(int mPoints) {
        this.mPoints = mPoints;
    }



}
