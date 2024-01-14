package com.example.daolab.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.daolab.dao.CardDao;
import com.example.daolab.dao.DeckDao;
import com.example.daolab.items.Card;
import com.example.daolab.items.Deck;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Card.class, Deck.class}, version = 1, exportSchema = false)
public abstract class CardRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile CardRoomDatabase INSTANCE;
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                CardDao cardDao = INSTANCE.cardDao();
                DeckDao deckdao = INSTANCE.deckDao();
                cardDao.deleteAllCards();
            });
        }
    };

    public static CardRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CardRoomDatabase.class, "cards_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CardDao cardDao();

    public abstract DeckDao deckDao();
}
