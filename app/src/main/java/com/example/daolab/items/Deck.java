package com.example.daolab.items;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "decks_table")
public class Deck {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "progress")
    private int mProgress;

    public Deck(@NonNull String name) {
        this.mName = name;
        this.mProgress = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Deck{" +
                ", mName='" + mName + '\'' +
                ", mProgress=" + mProgress +
                '}';
    }


    @NonNull
    public String getName() {
        return mName;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }
}
