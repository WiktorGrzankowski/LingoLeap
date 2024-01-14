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
import com.example.daolab.adapters.AudioListAdapter;
import com.example.daolab.adapters.DeckListAdapter;
import com.example.daolab.items.Card;
import com.example.daolab.items.Deck;
import com.example.daolab.items.Difficulty;
import com.example.daolab.models.DeckViewModel;

import java.util.Arrays;
import java.util.Objects;

public class ReadingActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView audios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        setupToolbar();

        audios = findViewById(R.id.audiosList);
        AudioListAdapter adapter = new AudioListAdapter(Arrays.asList("Man Utd fans", "Arsenal fans"));
        audios.setAdapter(adapter);
        audios.setLayoutManager(new LinearLayoutManager(this));
        audios.addItemDecoration(new DividerItemDecoration(audios.getContext(), DividerItemDecoration.VERTICAL));
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