package com.example.mycoin.entities;

public class Match {

    private String mWinner;
    private int mPointsPlayer1;
    private int mPointsPlayer2;
    private boolean mShouldShowNextQuestion;

    public Match() {
    }

    public String getWinner() {
        return mWinner;
    }

    public void setWinner(String mWinner) {
        this.mWinner = mWinner;
    }

    public int getPointsPlayer1() {
        return mPointsPlayer1;
    }

    public void setPointsPlayer1(int mPointsPlayer1) {
        this.mPointsPlayer1 = mPointsPlayer1;
    }

    public int getPointsPlayer2() {
        return mPointsPlayer2;
    }

    public void setPointsPlayer2(int mPointsPlayer2) {
        this.mPointsPlayer2 = mPointsPlayer2;
    }

    public boolean getShouldShowNextQuestion() {
        return mShouldShowNextQuestion;
    }

    public void setShouldShowNextQuestion(boolean mShouldShowNextQuestion) {
        this.mShouldShowNextQuestion = mShouldShowNextQuestion;
    }

    enum GameStatus {
        CREATED,
        JOINED,
        IN_PROGRESS,
        FINISHED
    }
}
