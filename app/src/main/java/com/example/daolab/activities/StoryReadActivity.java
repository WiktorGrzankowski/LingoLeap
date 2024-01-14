package com.example.daolab.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.daolab.R;

import java.util.Arrays;
import java.util.List;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class StoryReadActivity extends AppCompatActivity {

    private Toolbar toolbar;
    int audioResId;
    private String question;
    private List<String> answers;
    private String storyName;
    int correctAnswer;

    private Button answerButton1, answerButton2, answerButton3, answerButton4;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);

        storyName = getIntent().getStringExtra("STORY_NAME");

        // Set up the toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(storyName);
        setSupportActionBar(toolbar);

        // Initialize MediaPlayer
        switch (storyName) {
            default:
                audioResId = R.raw.audio_adele;
                question = "Oi mate, did ya catch the footy match last night? It was a proper belter!" +
                        " The Reds were up against the Blues. The Reds' striker, a real nifty lad, was on fire. " +
                        "He nutmegged one bloke, then another, and banged in a screamer from 20 yards out. The crowd went mental!" +
                        " But, in the last tick, the Blues' winger, who’d been a right ghost all game, " +
                        "whipped in a peach of a cross and their striker nodded it in. Ended in a 1-all draw. Proper gutted, I was!" +
                        "\nQuestion: Which remarkable feat did the Reds' striker achieve during the match?";
                answers = Arrays.asList("Scored a hat-trick", "Had an affair with the journalist from Sky",
                        "Threaded two defenders and scored a worldie", "Scored an own goal");
                correctAnswer = R.id.answerButton3;
        }

        TextView questionText = findViewById(R.id.questionText);
        questionText.setText(question);


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
            showFireworks();
        } else {
            selectedButton.setBackgroundColor(Color.RED);
        }
    }

    private void showFireworks() {
        KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);
    }
}