package com.example.daolab.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.daolab.activities.DeckActivity;
import com.example.daolab.holders.CardViewHolder;
import com.example.daolab.items.Card;
import com.example.daolab.models.DeckViewModel;

public class CardListAdapter extends ListAdapter<Card, CardViewHolder> {

    private final DeckActivity deckActivity;
    private final DeckViewModel deckViewModel;

    public CardListAdapter(@NonNull DiffUtil.ItemCallback<Card> diffCallback,
                           DeckActivity deckActivity, DeckViewModel deckViewModel) {
        super(diffCallback);
        this.deckActivity = deckActivity;
        this.deckViewModel = deckViewModel;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CardViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card current = getItem(position);
        holder.bind(current.getId(), current.getQuestion(), current.getAnswer(),
                current.getDifficulty(), deckViewModel);

        holder.itemView.setOnClickListener(view -> deckActivity.showCardDialog(current));
    }

    public static class CardDiff extends DiffUtil.ItemCallback<Card> {

        @Override
        public boolean areItemsTheSame(@NonNull Card oldItem, @NonNull Card newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Card oldItem, @NonNull Card newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
