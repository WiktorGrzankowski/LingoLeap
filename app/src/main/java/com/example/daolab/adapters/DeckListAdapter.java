package com.example.daolab.adapters;

import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.daolab.activities.DeckActivity;
import com.example.daolab.holders.DeckViewHolder;
import com.example.daolab.items.Deck;
import com.example.daolab.models.DeckViewModel;

public class DeckListAdapter extends ListAdapter<Deck, DeckViewHolder> {
    final DeckViewModel deckViewModel;

    public DeckListAdapter(@NonNull DiffUtil.ItemCallback<Deck> diffCallback,
                           DeckViewModel deckViewModel) {
        super(diffCallback);
        this.deckViewModel = deckViewModel;
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DeckViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DeckViewHolder holder, int position) {
        Deck current = getItem(position);
        holder.bind(current.getName(), deckViewModel.getDeckSize(current.getName()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DeckActivity.class);
            intent.putExtra("deckName", current.getName());
            view.getContext().startActivity(intent);
        });
    }

    public static class DeckDiff extends DiffUtil.ItemCallback<Deck> {

        @Override
        public boolean areItemsTheSame(@NonNull Deck oldItem, @NonNull Deck newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Deck oldItem, @NonNull Deck newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
