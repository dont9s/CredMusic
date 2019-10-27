package com.gojek.weather.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.gojek.weather.R;
import com.gojek.weather.service.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songs;
    private MutableLiveData<Pair<Song, Boolean>> currentSong;

    public SongAdapter(List<Song> songs, MutableLiveData<Pair<Song, Boolean>> currentSong) {
        this.songs = songs;
        this.currentSong = currentSong;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {

        Song song = songs.get(position);
        if (holder != null) {
            holder.tvSong.setText(song.getSong());
            holder.tvArtist.setText(song.getArtists());

            Glide.with(holder.itemView.getContext())
                    .load(song.getCoverImage())
                    .into(holder.ivCover);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentSong.setValue(new Pair<>(songs.get(position), true));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSong, tvArtist;
        private ImageView ivCover;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            tvSong = itemView.findViewById(R.id.tv_song);
            ivCover = itemView.findViewById(R.id.iv_cover);
        }
    }
}
