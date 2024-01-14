package com.example.daolab.items;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cards_table")
public class Card implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "question")
    private String mQuestion;

    @NonNull
    @ColumnInfo(name = "answer")
    private String mAnswer;

    @NonNull
    @ColumnInfo(name = "difficulty")
    private Difficulty mDifficulty;

    @NonNull
    @ColumnInfo(name = "deckName")
    private final String mDeckName;

    public Card(@NonNull String question, @NonNull String answer, @NonNull Difficulty difficulty, @NonNull String deckName) {
        this.mQuestion = question;
        this.mAnswer = answer;
        this.mDifficulty = difficulty;
        this.mDeckName = deckName;
    }

    @NonNull
    @Override
    public String toString() {
        return "Card{" +
                "mId=" + mId +
                ", mQuestion='" + mQuestion + '\'' +
                ", mAnswer='" + mAnswer + '\'' +
                ", mDifficulty=" + mDifficulty +
                ", mDeckName='" + mDeckName + '\'' +
                '}';
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    @NonNull
    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(@NonNull String mQuestion) {
        this.mQuestion = mQuestion;
    }

    @NonNull
    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(@NonNull String mAnswer) {
        this.mAnswer = mAnswer;
    }

    @NonNull
    public Difficulty getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(Difficulty mDifficulty) {
        this.mDifficulty = mDifficulty;
    }

    public String getDeckName() {
        return mDeckName;
    }
}