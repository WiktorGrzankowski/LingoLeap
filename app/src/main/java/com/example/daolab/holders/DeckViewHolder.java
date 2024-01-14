package com.example.daolab.holders;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.google.android.material.chip.Chip;

public class DeckViewHolder extends RecyclerView.ViewHolder {
    private final TextView deckNameTextView;
    private final Chip deckCardNumberChip;

    private DeckViewHolder(View itemView) {
        super(itemView);
        deckNameTextView = itemView.findViewById(R.id.deckName);
        deckCardNumberChip = itemView.findViewById(R.id.deckCardNumber);
    }

    public static DeckViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deck_view_holder, parent, false);
        return new DeckViewHolder(view);
    }

    public void bind(String deckName, int deckCardNumber) {
        deckNameTextView.setText(deckName);

        Resources res = deckCardNumberChip.getContext().getResources();
        String deckCardNumberString =
                deckCardNumber == 1
                        ? res.getString(R.string.card_number_singular, deckCardNumber)
                        : res.getString(R.string.card_number_plural, deckCardNumber);
        deckCardNumberChip.setText(deckCardNumberString);
    }
}
