package com.example.daolab.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.daolab.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AudioListenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    int audioResId;
    private String audioName;
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button rerunButton;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_listen);

        audioName = getIntent().getStringExtra("AUDIO_NAME");

        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(audioName);
        setSupportActionBar(toolbar);

        // Initialize MediaPlayer
        switch (audioName) {
            case "Adele":
                audioResId = R.raw.audio_adele;
                break;
            case "Phineas and Ferb":
                audioResId = R.raw.audio_phineas;
                break;
            default:
                audioResId = R.raw.audio_adele;
        }
        mediaPlayer = MediaPlayer.create(this, audioResId);

        // Set up the play button
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseAudio();
                } else {
                    startAudio();
                }
            }
        });

        // Set up the rerun button
        rerunButton = findViewById(R.id.rerunButton);
        rerunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rerunAudio();
            }
        });
    }

    private void startAudio() {
        mediaPlayer.start();
        isPlaying = true;
        playButton.setText("Pause");
    }

    private void pauseAudio() {
        mediaPlayer.pause();
        isPlaying = false;
        playButton.setText("Play");
    }

    private void rerunAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, audioResId);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
