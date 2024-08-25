package com.example.mycoin.entities;

public class Question {
    private String mQuestion;
    private String mAnswer1;
    private String mAnswer2;
    private String mAnswer3;
    private String mAnswer4;
    private String mCorrectAnswer;

    public Question(String question, String answer1, String answer2, String answer3,
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

    public String getAnswer1() {
        return mAnswer1;
    }

    public String getAnswer2() {
        return mAnswer2;
    }

    public String getAnswer3() {
        return mAnswer3;
    }

    public String getAnswer4() {
        return mAnswer4;
    }

    public String getIsRight() {
        return mCorrectAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mAnswer1='" + mAnswer1 + '\'' +
                ", mAnswer2='" + mAnswer2 + '\'' +
                ", mAnswer3='" + mAnswer3 + '\'' +
                ", mAnswer4='" + mAnswer4 + '\'' +
                ", mCorrectAnswer='" + mCorrectAnswer + '\'' +
                '}';
    }
}
