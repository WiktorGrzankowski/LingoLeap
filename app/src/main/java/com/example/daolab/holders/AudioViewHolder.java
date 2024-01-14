package com.example.daolab.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;

public class AudioViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;

    public AudioViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.audioName);
    }
}
