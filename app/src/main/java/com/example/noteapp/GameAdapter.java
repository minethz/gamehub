package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList;
    private Context context;
    private OnGameClickListener onGameClickListener;

    public GameAdapter(List<Game> gameList, Context context, OnGameClickListener onGameClickListener) {
        this.gameList = gameList;
        this.context = context;
        this.onGameClickListener = onGameClickListener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view, onGameClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.titleTextView.setText(game.getTitle());
        Glide.with(context).load(game.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        ImageView imageView;
        OnGameClickListener onGameClickListener;

        public GameViewHolder(@NonNull View itemView, OnGameClickListener onGameClickListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
            this.onGameClickListener = onGameClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGameClickListener.onGameClick(getAdapterPosition());
        }
    }

    public interface OnGameClickListener {
        void onGameClick(int position);
    }
}
