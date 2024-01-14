package com.example.daolab.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.items.Difficulty;
import com.example.daolab.models.DeckViewModel;

public class CardViewHolder extends RecyclerView.ViewHolder {
    private final TextView cardQuestionTextView;
    private final TextView cardAnswerTextView;
    private final TextView cardDifficultyTextView;
    private final ImageButton cardDeleteButton;

    private CardViewHolder(View itemView) {
        super(itemView);
        cardQuestionTextView = itemView.findViewById(R.id.cardQuestion);
        cardAnswerTextView = itemView.findViewById(R.id.cardAnswer);
        cardDifficultyTextView = itemView.findViewById(R.id.cardDifficulty);
        cardDeleteButton = itemView.findViewById(R.id.cardDeleteButton);
    }

    public static CardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_holder, parent, false);
        return new CardViewHolder(view);
    }

    public void bind(long cardId, String question, String answer, Difficulty difficulty,
                     DeckViewModel deckViewModel) {
        cardQuestionTextView.setText(question);
        cardAnswerTextView.setText(answer);
        cardDifficultyTextView.setText(difficulty.getNameFromResources());

        cardDeleteButton.setOnClickListener(view -> deckViewModel.deleteCard(cardId));
    }

}
