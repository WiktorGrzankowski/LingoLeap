package com.example.daolab.activities;

import android.os.Bundle;
import android.util.Log;
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

public class AudioListenActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String audioName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        audioName = getIntent().getExtras().getString("AUDIO_NAME");

        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(audioName);
        setSupportActionBar(toolbar);
    }
}

