package com.example.onlinequiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    private int id;
    private String question;
    private String op1;
    private String op2;
    private String op3;
    private String op4;
    private int answerNr;
    private String difficulty;
    private String  categoryID;

    public Question(String question, String op1, String op2, String op3, String op4, int answerNr, String difficulty, String categoryID) {
        this.question = question;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.answerNr = answerNr;
        this.difficulty = difficulty;
        this.categoryID = categoryID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String  getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.question);
        dest.writeString(this.op1);
        dest.writeString(this.op2);
        dest.writeString(this.op3);
        dest.writeString(this.op4);
        dest.writeInt(this.answerNr);
        dest.writeString(this.difficulty);
        dest.writeString(this.categoryID);
    }

    public Question(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.op1 = in.readString();
        this.op2 = in.readString();
        this.op3 = in.readString();
        this.op4 = in.readString();
        this.answerNr = in.readInt();
        this.difficulty = in.readString();
        this.categoryID = in.readString();
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    public static String[] getAllDifficultyLevels(){
        return new String []{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };

    }
}
