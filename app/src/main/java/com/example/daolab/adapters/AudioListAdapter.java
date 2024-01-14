package com.example.daolab.adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daolab.R;
import com.example.daolab.activities.AudioListenActivity;

import java.lang.reflect.Array;
import java.util.List;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    private List<String> audioNames;

    public AudioListAdapter(List<String> audioNames) {
        this.audioNames = audioNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_view_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String name = audioNames.get(position);
        holder.audioName.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AudioListenActivity.class);
//                intent.putExtra("AUDIO_NAME", name);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioNames.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView audioName;

        ViewHolder(View itemView) {
            super(itemView);
            audioName = itemView.findViewById(R.id.audioName);
        }
    }
}
