package com.example.daolab.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.adapters.DeckListAdapter;
import com.example.daolab.items.Deck;
import com.example.daolab.models.DeckViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DecksActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView deckList;
    ExtendedFloatingActionButton newDeckFAB;

    private DeckViewModel mDeckViewModel;
    private DeckListAdapter adapter;

    private void createDeck(String deckName) {
        if (!deckName.isEmpty()) {
            // Capitalize the first letter.
            deckName = deckName.substring(0, 1).toUpperCase() + deckName.substring(1);

            Deck deck = new Deck(deckName);
            if (mDeckViewModel.insert(deck)) {
                Intent intent = new Intent(DecksActivity.this, DeckActivity.class);
                intent.putExtra("deckName", deckName);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.deck_already_exists, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);

        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDeckViewModel = new ViewModelProvider(this).get(DeckViewModel.class);

        // Set up the deck list.
        deckList = findViewById(R.id.audiosList);
        adapter = new DeckListAdapter(new DeckListAdapter.DeckDiff(), mDeckViewModel);
        deckList.setAdapter(adapter);
        deckList.setLayoutManager(new LinearLayoutManager(this));
        deckList.addItemDecoration(new DividerItemDecoration(deckList.getContext(), DividerItemDecoration.VERTICAL));

        // Update the cached copy of the words in the adapter.
//        mDeckViewModel.getAllDecks().observe(this, adapter::submitList);
        LiveData<List<Deck>> alld = mDeckViewModel.getAllDecks();
        filterByName(alld, "Pub Banter").observe(this, adapter::submitList);
//        alld.observe(this, adapter::submitList);

        // Set up the new deck button.
        newDeckFAB = findViewById(R.id.newDeckFAB);
        newDeckFAB.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.deck_new_title);

            // Set up the input.
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    | InputType.TYPE_TEXT_VARIATION_NORMAL);
            input.setHint(R.string.deck_new_name_hint);
            builder.setView(input);

            // Set up the buttons.
            builder.setPositiveButton(R.string.button_create, (dialog, which) -> createDeck(input.getText().toString()));
            builder.setNegativeButton(R.string.button_cancel, (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    private LiveData<List<Deck>> filterByName(LiveData<List<Deck>> originalLiveData, String name) {
        return Transformations.switchMap(originalLiveData, inputList -> {
            MutableLiveData<List<Deck>> filteredListLiveData = new MutableLiveData<>();

            // Filter the elements based on the condition (e.g., starts with filterKeyword)
            List<Deck> filteredList = new ArrayList<>();
            for (Deck item : inputList) {
                if (!item.getName().equals(name)) {
                    filteredList.add(item);
                }
            }

            // Update the filtered list in the MutableLiveData
            filteredListLiveData.setValue(filteredList);

            return filteredListLiveData;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();

        // Update the number of cards in the decks.
        adapter.notifyDataSetChanged();
    }
}