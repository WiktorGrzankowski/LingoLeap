package com.example.daolab.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.example.daolab.R;
import com.example.daolab.items.Card;
import com.example.daolab.models.DeckViewModel;

import java.util.Collections;
import java.util.List;

public class LearningActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView cardQuestion;
    TextView cardAnswer;
    TextView cardIndex;
    Button previousCardButton;
    Button nextCardButton;
    CardView cardView;
    DeckViewModel mDeckViewModel;
    private List<Card> cards;
    private int currentCardIndex;

    private void displayCard() {
        try {
            Card card = cards.get(currentCardIndex);
            cardQuestion.setText(card.getQuestion());
            cardAnswer.setText(R.string.card_hidden_answer);
            cardIndex.setText(getResources().getString(R.string.card_index, currentCardIndex + 1, cards.size()));
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(this, R.string.card_not_found, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        String deckName = getIntent().getExtras().getString("deckName");

        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(deckName);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        cardQuestion = findViewById(R.id.cardQuestionTitle);
        cardAnswer = findViewById(R.id.cardAnswerTitle);
        cardIndex = findViewById(R.id.cardIndex);
        previousCardButton = findViewById(R.id.previousCardButton);
        nextCardButton = findViewById(R.id.nextCardButton);

        // Get all cards from the current deck and shuffle them.
        try {
            mDeckViewModel = new ViewModelProvider(this).get(DeckViewModel.class);
            Thread thread = new Thread(() -> cards = mDeckViewModel.getAllCardsOfDeckList(deckName));
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.shuffle(cards);

        // Set up the "next" and "previous" buttons.
        previousCardButton.setOnClickListener(view -> {
            if (currentCardIndex > 0) {
                currentCardIndex--;
                displayCard();
            }
        });
        nextCardButton.setOnClickListener(view -> {
            if (currentCardIndex < cards.size() - 1) {
                currentCardIndex++;
                displayCard();
            }
        });

        // Reveal the answer upon clicking the card.
        cardView = findViewById(R.id.cardView);
        cardView.setOnClickListener(view -> {
            Card card = cards.get(currentCardIndex);
            cardAnswer.setText(card.getAnswer());
        });

        currentCardIndex = 0;
        displayCard();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}