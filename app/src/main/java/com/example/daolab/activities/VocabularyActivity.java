package com.example.daolab.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.adapters.DeckListAdapter;
import com.example.daolab.models.DeckViewModel;

import java.util.Objects;

public class VocabularyActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView deckList;
    private DeckViewModel mDeckViewModel;
    private DeckListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        setupToolbar();

        mDeckViewModel = new ViewModelProvider(this).get(DeckViewModel.class);
        deckList = findViewById(R.id.vocabTopics);
        adapter = new DeckListAdapter(new DeckListAdapter.DeckDiff(), mDeckViewModel);
        deckList.setAdapter(adapter);
        deckList.setLayoutManager(new LinearLayoutManager(this));
        deckList.addItemDecoration(new DividerItemDecoration(deckList.getContext(), DividerItemDecoration.VERTICAL));
        // Currently, just for MVP, one deck for vocabulary.
        mDeckViewModel.getDeck("Pub Banter").observe(this, adapter::submitList);
    }

    private void setupToolbar() {
        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back button press
            }
        });
    }
}
