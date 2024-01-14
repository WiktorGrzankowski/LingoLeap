package com.example.daolab.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.adapters.AudioListAdapter;

import java.util.Arrays;
import java.util.Objects;

public class ListeningActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView audios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_all);
        setupToolbar();

        audios = findViewById(R.id.audiosList);
        AudioListAdapter adapter = new AudioListAdapter(Arrays.asList("Adele", "Phineas and Ferb"));
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
