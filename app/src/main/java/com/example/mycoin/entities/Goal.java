package com.example.mycoin.entities;

public class Goal {

    private String mDescription;
    private int mPoints;
    private boolean mIsDone;

    public Goal() {
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int mPoints) {
        this.mPoints = mPoints;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setIsDone(boolean mIsDone) {
        this.mIsDone = mIsDone;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "mDescription='" + mDescription + '\'' +
                ", mPoints=" + mPoints +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
