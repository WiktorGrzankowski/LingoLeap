package com.example.daolab.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.daolab.items.Card;
import com.example.daolab.items.Deck;

import java.util.List;

@Dao
public interface DeckDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDeck(Deck deck);

    @Query("DELETE FROM decks_table WHERE name = :deckName")
    int deleteDeck(String deckName);

    @Query("SELECT * FROM decks_table")
    LiveData<List<Deck>> getAllDecks();

    @Query("SELECT * FROM decks_table where name = :deckName")
    LiveData<List<Deck>> getDeck(String deckName);

    @Query("SELECT * FROM cards_table WHERE deckName = :deckName")
    LiveData<List<Card>> getCardsOfDeck(String deckName);

    // Pure list, not LiveData.
    @Query("SELECT * FROM cards_table WHERE deckName = :deckName")
    List<Card> getCardsOfDeckList(String deckName);

    @Query("SELECT COUNT(*) FROM cards_table WHERE deckName = :deckName")
    int getDeckSize(String deckName);

    @Query("SELECT COUNT(*) FROM decks_table WHERE name = :deckName")
    int deckExists(String deckName);
}