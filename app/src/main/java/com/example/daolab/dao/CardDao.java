package com.example.daolab.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.daolab.items.Card;
import com.example.daolab.items.Difficulty;

@Dao
public interface CardDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCard(Card card);

    @Query("DELETE FROM cards_table where id = :cardId")
    int deleteCard(long cardId);

    @Query("DELETE FROM cards_table")
    void deleteAllCards();

    @Query("DELETE FROM cards_table where deckName = :deckName")
    int deleteAllFromDeck(String deckName);

    @Query("UPDATE cards_table SET question = :question, answer = :answer, difficulty = :difficulty where id = :id")
    int updateCard(long id, String question, String answer, Difficulty difficulty);
}