package com.movie.nari.movieapp.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.nari.movieapp.MyApplication;
import com.movie.nari.movieapp.R;
import com.movie.nari.movieapp.model.NowPlaying;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NowPlaying> nowPlayingList;
    private boolean isLandscape;

    static class PortraitViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView posterView;
        TextView titleView;
        TextView overviewView;

        PortraitViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            posterView = v.findViewById(R.id.poster_image);
            titleView = v.findViewById(R.id.title_view);
            overviewView = v.findViewById(R.id.overview_view);
        }

        void bindView(NowPlaying nowPlaying) {

            Picasso.with(context)
                    .load(MyApplication.IMAGE_BASE_URL + nowPlaying.getPoster_path())
                    .into(posterView);

            titleView.setText(nowPlaying.getTitle());
            overviewView.setText(nowPlaying.getOverview());
        }
    }

    static class LandscapeViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView posterView;
        TextView titleView;
        TextView overviewView;

        LandscapeViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            posterView = v.findViewById(R.id.poster_image);
            titleView = v.findViewById(R.id.title_view);
            overviewView = v.findViewById(R.id.overview_view);
        }

        void bindView(NowPlaying nowPlaying) {

            Picasso.with(context)
                    .load(MyApplication.IMAGE_BASE_URL + nowPlaying.getBackdrop_path())
                    .into(posterView);

            titleView.setText(nowPlaying.getTitle());
            overviewView.setText(nowPlaying.getOverview());
        }
    }

    public RecyclerViewAdapter(List<NowPlaying> list, boolean isLandscape) {
        this.nowPlayingList = list;
        this.isLandscape = isLandscape;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        isLandscape = parent.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        Timber.d("is landscape? " + isLandscape);
        if (isLandscape) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_now_playing_landscape, parent, false);
            return new LandscapeViewHolder(v, parent.getContext());
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_now_playing_portrait, parent, false);
            return new PortraitViewHolder(v, parent.getContext());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PortraitViewHolder) {
            ((PortraitViewHolder) holder).bindView(nowPlayingList.get(position));
        } else if (holder instanceof LandscapeViewHolder) {
            ((LandscapeViewHolder) holder).bindView(nowPlayingList.get(position));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return nowPlayingList.size();
    }

    public void update(List<NowPlaying> nowPlayingList) {
        this.nowPlayingList = nowPlayingList;
        notifyDataSetChanged();
    }
}
