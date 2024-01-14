package com.example.daolab.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.daolab.dao.CardDao;
import com.example.daolab.dao.DeckDao;
import com.example.daolab.database.CardRoomDatabase;
import com.example.daolab.items.Card;
import com.example.daolab.items.Deck;
import com.example.daolab.items.Difficulty;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CardsRepository {
    private static final int DECK_ALREADY_EXISTS = -1;

    private final CardDao mCardDao;
    private final DeckDao mDeckDao;
    private final LiveData<List<Deck>> mAllDecks;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public CardsRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mCardDao = db.cardDao();
        mDeckDao = db.deckDao();
        mAllDecks = mDeckDao.getAllDecks();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Deck>> getAllDecks() {
        return mAllDecks;
    }

    public LiveData<List<Card>> getAllCardsOfDeck(String deckName) {
        return mDeckDao.getCardsOfDeck(deckName);
    }

    public List<Card> getAllCardsOfDeckList(String deckName) {
        return mDeckDao.getCardsOfDeckList(deckName);
    }


    public int getDeckSize(String deckName) {
        return getDeckSizeHelper(deckName);
    }

    Integer getDeckSizeHelper(String deckName) {
        try {
            return new GetDeckSizeTask(mDeckDao, deckName).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Returns 0 if inserted successfully, 1 otherwise.
    public int insert(Deck deck) {
        if (deckExists(deck.getName()))
            return DECK_ALREADY_EXISTS;
        CardRoomDatabase.databaseWriteExecutor.execute(() -> mDeckDao.insertDeck(deck));
        return 0;
    }

    public boolean deckExists(String deckName) {
        return deckExistsHelper(deckName) > 0;
    }

    Integer deckExistsHelper(String deckName) {
        try {
            return new DeckExistsTask(mDeckDao, deckName).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long insert(Card card) {
        final long[] result = new long[1];
        CardRoomDatabase.databaseWriteExecutor.execute(() -> result[0] = mCardDao.insertCard(card));
        return result[0];
    }

    public void deleteDeck(String deckName) {
        try {
            new DeleteDeckTask(mDeckDao, deckName).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(long cardId) {
        try {
            new DeleteCardTask(mCardDao, cardId).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCardsOfDeck(String deckName) {
        try {
            new DeleteAllCardsOfDeck(mCardDao, deckName).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long insertCard(Card card) {
        try {
            return new InsertCardTask(mCardDao, card).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateCard(long cardId, String question, String answer, Difficulty difficulty) {
        try {
            new UpdateCardTask(mCardDao, cardId, question, answer, difficulty).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetDeckSizeTask extends AsyncTask<Void, Void, Integer> {
        final DeckDao deckDao;
        final String deckName;

        public GetDeckSizeTask(DeckDao deckDao, String deckName) {
            super();
            this.deckDao = deckDao;
            this.deckName = deckName;
        }

        @Override
        protected Integer doInBackground(Void... notes) {
            return deckDao.getDeckSize(deckName);
        }
    }

    private static class DeckExistsTask extends AsyncTask<Void, Void, Integer> {
        final DeckDao deckDao;
        final String deckName;

        public DeckExistsTask(DeckDao deckDao, String deckName) {
            super();
            this.deckDao = deckDao;
            this.deckName = deckName;
        }

        @Override
        protected Integer doInBackground(Void... notes) {
            return deckDao.deckExists(deckName);
        }
    }

    private static class DeleteDeckTask extends AsyncTask<Void, Void, Integer> {
        final DeckDao deckDao;
        final String deckName;

        public DeleteDeckTask(DeckDao deckDao, String deckName) {
            super();
            this.deckDao = deckDao;
            this.deckName = deckName;
        }

        @Override
        protected Integer doInBackground(Void... notes) {
            return deckDao.deleteDeck(deckName);
        }
    }

    private static class DeleteCardTask extends AsyncTask<Void, Void, Integer> {
        final CardDao cardDao;
        final long cardId;

        public DeleteCardTask(CardDao cardDao, long cardId) {
            super();
            this.cardDao = cardDao;
            this.cardId = cardId;
        }

        @Override
        protected Integer doInBackground(Void... notes) {
            return cardDao.deleteCard(cardId);
        }
    }

    private static class DeleteAllCardsOfDeck extends AsyncTask<Void, Void, Integer> {
        final CardDao cardDao;
        final String deckName;

        public DeleteAllCardsOfDeck(CardDao cardDao, String deckName) {
            super();
            this.cardDao = cardDao;
            this.deckName = deckName;
        }

        @Override
        protected Integer doInBackground(Void... notes) {
            return cardDao.deleteAllFromDeck(deckName);
        }
    }

    private static class InsertCardTask extends AsyncTask<Void, Void, Long> {
        final CardDao cardDao;
        final Card card;

        public InsertCardTask(CardDao cardDao, Card card) {
            super();
            this.cardDao = cardDao;
            this.card = card;
        }

        @Override
        protected Long doInBackground(Void... notes) {
            return cardDao.insertCard(card);
        }
    }

    private static class UpdateCardTask extends AsyncTask<Void, Void, Integer> {
        final CardDao cardDao;
        final long cardId;
        final String question;
        final String answer;
        final Difficulty difficulty;

        public UpdateCardTask(CardDao cardDao, long cardId, String question, String answer, Difficulty difficulty) {
            super();
            this.cardDao = cardDao;
            this.cardId = cardId;
            this.question = question;
            this.answer = answer;
            this.difficulty = difficulty;
        }

        @Override
        protected Integer doInBackground(Void... notes) {
            return cardDao.updateCard(cardId, question, answer, difficulty);
        }
    }

}
