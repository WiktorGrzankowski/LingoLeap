package com.example.daolab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.daolab.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        setUpButton(R.id.buttonLayoutFlashcards, DecksActivity.class);
        setUpButton(R.id.buttonLayoutListening, ListeningActivity.class);
        setUpButton(R.id.buttonLayoutVocabulary, VocabularyActivity.class);
        setUpButton(R.id.buttonLayoutReading, ReadingActivity.class);
    }

    private void setUpButton(int layoutId, final Class<?> activityToStart) {
        ConstraintLayout buttonLayout = findViewById(layoutId);
        buttonLayout.setClickable(true);
        buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, activityToStart);
                startActivity(intent);
            }
        });
    }

}