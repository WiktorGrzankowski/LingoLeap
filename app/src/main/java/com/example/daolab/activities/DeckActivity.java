package com.example.daolab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.adapters.CardListAdapter;
import com.example.daolab.items.Card;
import com.example.daolab.items.Difficulty;
import com.example.daolab.models.DeckViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.slider.Slider;

public class DeckActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView cardList;
    ExtendedFloatingActionButton startLearningFAB;

    private static final long EMPTY_CARD_ID = -1;
    private DeckViewModel mDeckViewModel;
    private String deckName;

    private void createOrUpdateCard(long id, String question, String answer, Difficulty difficulty) {
        if (question.isEmpty() || answer.isEmpty())
            return;

        if (id == EMPTY_CARD_ID) {
            // Create new card.
            Card newCard = new Card(question, answer, difficulty, deckName);
            mDeckViewModel.insert(newCard);
        } else {
            // Update existing card.
            mDeckViewModel.updateCard(id, question, answer, difficulty);
        }
    }

    public void showCardDialog(Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(card != null ? R.string.card_edit_title : R.string.card_new_title);

        // Set up the layout.
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_card_details, null);
        builder.setView(dialogView);

        EditText cardQuestionInput = dialogView.findViewById(R.id.cardQuestionInput);
        EditText cardAnswerInput = dialogView.findViewById(R.id.cardAnswerInput);
        Slider difficultySlider = dialogView.findViewById(R.id.difficultySlider);

        if (card != null) {
            cardQuestionInput.setText(card.getQuestion());
            cardAnswerInput.setText(card.getAnswer());
            difficultySlider.setValue(card.getDifficulty().getLevel());
        }

        // Set up the buttons.
        builder.setPositiveButton(R.string.button_create, (dialog, which) -> createOrUpdateCard(
                card != null ? card.getId() : EMPTY_CARD_ID,
                cardQuestionInput.getText().toString(),
                cardAnswerInput.getText().toString(),
                Difficulty.getByLevel((int) difficultySlider.getValue())
        ));
        builder.setNegativeButton(R.string.button_cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        deckName = getIntent().getExtras().getString("deckName");

        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(deckName);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set up the card list.
        cardList = findViewById(R.id.cardList);
        mDeckViewModel = new ViewModelProvider(this).get(DeckViewModel.class);
        final CardListAdapter adapter = new CardListAdapter(new CardListAdapter.CardDiff(), this, mDeckViewModel);
        cardList.setAdapter(adapter);
        cardList.setLayoutManager(new LinearLayoutManager(this));
        cardList.addItemDecoration(new DividerItemDecoration(cardList.getContext(), DividerItemDecoration.VERTICAL));

        // Update the cached copy of the words in the adapter.
        mDeckViewModel.getAllCardsOfDeck(deckName).observe(this, adapter::submitList);

        // Set up the "start learning" button.
        startLearningFAB = findViewById(R.id.startLearningFAB);
        startLearningFAB.setOnClickListener(view -> {
            Intent intent = new Intent(this, LearningActivity.class);
            intent.putExtra("deckName", deckName);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_deck_content_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete_deck) {
            mDeckViewModel.deleteDeck(deckName);
            finish();
        }
        if (itemId == R.id.action_create_flashcard) {
            showCardDialog(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}