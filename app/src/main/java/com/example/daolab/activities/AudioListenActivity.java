package com.example.daolab.activities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.daolab.R;

import java.util.Arrays;
import java.util.List;

public class AudioListenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    int audioResId;
    private String question;
    private List<String> answers;
    private String audioName;
    int correctAnswer;
    private MediaPlayer mediaPlayer;
    private Button playButton;

    private Button answerButton1, answerButton2, answerButton3, answerButton4;
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
                question = "What did Adele after she met Beyonce?";
                answers = Arrays.asList("She kept her cool all the time", "She was getting her makeup done",
                        "She thanked her for her work", "She tore apart outside the building");
                correctAnswer = R.id.answerButton4;
                break;
            case "Phineas and Ferb":
                audioResId = R.raw.audio_phineas;
                question = "What did Perry the Platypus say?";
                answers = Arrays.asList("Let's build a roller-coaster", "I'm a secret agent",
                        "I lay eggs despite being a mammal", "Nothing");
                correctAnswer = R.id.answerButton3;
                break;
            default:
                audioResId = R.raw.audio_adele;
        }

        TextView questionText = findViewById(R.id.questionText);
        questionText.setText(question);



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
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton1.setText(answers.get(0));
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton2.setText(answers.get(1));
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton3.setText(answers.get(2));
        answerButton4 = findViewById(R.id.answerButton4);
        answerButton4.setText(answers.get(3));
        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer((Button)view);
            }
        };

        answerButton1.setOnClickListener(answerListener);
        answerButton2.setOnClickListener(answerListener);
        answerButton3.setOnClickListener(answerListener);
        answerButton4.setOnClickListener(answerListener);
    }

    private void checkAnswer(Button selectedButton) {
        if (selectedButton.getId() == correctAnswer) {
            selectedButton.setBackgroundColor(Color.GREEN);
        } else {
            selectedButton.setBackgroundColor(Color.RED);
        }
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
