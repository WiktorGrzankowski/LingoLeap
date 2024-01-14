package com.example.daolab.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.adapters.StoryListAdapter;


import java.util.Arrays;
import java.util.Objects;

public class ReadingActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView stories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        setupToolbar();

        stories = findViewById(R.id.audiosList);
        StoryListAdapter adapter = new StoryListAdapter(Arrays.asList("Man Utd fans"));
        stories.setAdapter(adapter);
        stories.setLayoutManager(new LinearLayoutManager(this));
        stories.addItemDecoration(new DividerItemDecoration(stories.getContext(), DividerItemDecoration.VERTICAL));
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