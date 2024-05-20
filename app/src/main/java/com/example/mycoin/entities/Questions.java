package com.example.mycoin.entities;

public class Questions {
    private String mQuestion;
    private String mAnswer1;
    private String mAnswer2;
    private String mAnswer3;
    private String mAnswer4;
    private String mCorrectAnswer;

    public Questions(String question, String answer1, String answer2, String answer3,
                     String answer4, String correctAnswer) {
        mQuestion = question;
        mAnswer1 = answer1;
        mAnswer2 = answer2;
        mAnswer3 = answer3;
        mAnswer4 = answer4;
        mCorrectAnswer = correctAnswer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getAnswer1() {
        return mAnswer1;
    }

    public void setAnswer1(String mAnswer1) {
        this.mAnswer1 = mAnswer1;
    }

    public String getAnswer2() {
        return mAnswer2;
    }

    public void setAnswer2(String mAnswer2) {
        this.mAnswer2 = mAnswer2;
    }

    public String getAnswer3() {
        return mAnswer3;
    }

    public void setAnswer3(String mAnswer3) {
        this.mAnswer3 = mAnswer3;
    }

    public String getAnswer4() {
        return mAnswer4;
    }

    public void setAnswer4(String mAnswer4) {
        this.mAnswer4 = mAnswer4;
    }

    public String getIsRight() {
        return mCorrectAnswer;
    }

    public void setIsRight(String mCorrectAnswer) {
        this.mCorrectAnswer = mCorrectAnswer;
    }
}
