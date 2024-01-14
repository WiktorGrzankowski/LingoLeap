package com.example.daolab.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.daolab.items.Card;
import com.example.daolab.items.Deck;
import com.example.daolab.items.Difficulty;
import com.example.daolab.repository.CardsRepository;

import java.util.List;

public class DeckViewModel extends AndroidViewModel {

    private final CardsRepository mRepository;

    private final LiveData<List<Deck>> mAllDecks;

    public DeckViewModel(Application application) {
        super(application);
        mRepository = new CardsRepository(application);
        mAllDecks = mRepository.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return mAllDecks;
    }

    public LiveData<List<Deck>> getDeck(String deckName) {
        return mRepository.getDeck(deckName);
    }

    public LiveData<List<Card>> getAllCardsOfDeck(String deckName) {
        return mRepository.getAllCardsOfDeck(deckName);
    }

    public List<Card> getAllCardsOfDeckList(String deckName) {
        return mRepository.getAllCardsOfDeckList(deckName);
    }

    public int getDeckSize(String deckName) {
        return mRepository.getDeckSize(deckName);
    }

    // Repository insert returns 0 when insert succeeded.
    public boolean insert(Deck deck) {
        return mRepository.insert(deck) == 0;
    }

    public void deleteAllCardsOfDeck(String deckName) {
        mRepository.deleteAllCardsOfDeck(deckName);
    }

    public void deleteDeck(String deckName) {
        // Avoid having orphaned cards.
        mRepository.deleteAllCardsOfDeck(deckName);
        mRepository.deleteDeck(deckName);
    }

    public void deleteCard(long cardId) {
        mRepository.deleteCard(cardId);
    }

    public void insert(Card card) {
        long id = mRepository.insertCard(card);
        card.setId(id);
    }

    public void updateCard(long cardId, String question, String answer, Difficulty difficulty) {
        mRepository.updateCard(cardId, question, answer, difficulty);
    }
}