package com.movie.nari.movieapp.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.movie.nari.movieapp.MyApplication;
import com.movie.nari.movieapp.R;
import com.movie.nari.movieapp.model.NowPlaying;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NowPlaying> nowPlayingList;
    private boolean isLandscape;

    private final PublishSubject<Pair<Integer, View>> onClickSubject = PublishSubject.create();

    static class PortraitViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView posterView;
        TextView titleView;
        TextView overviewView;
        ProgressBar progressbar;

        PortraitViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            posterView = v.findViewById(R.id.poster_image);
            titleView = v.findViewById(R.id.title_view);
            overviewView = v.findViewById(R.id.overview_view);
            progressbar = v.findViewById(R.id.progressbar);
        }

        void bindView(NowPlaying nowPlaying) {

            Picasso.with(context)
                    .load(MyApplication.IMAGE_BASE_URL + nowPlaying.getPoster_path())
                    .into(posterView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if(progressbar != null)
                                progressbar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                        }
                    });

            titleView.setText(nowPlaying.getTitle());
            overviewView.setText(nowPlaying.getOverview());
        }
    }

    static class LandscapeViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView posterView;
        TextView titleView;
        TextView overviewView;
        ProgressBar progressbar;

        LandscapeViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            posterView = v.findViewById(R.id.poster_image);
            titleView = v.findViewById(R.id.title_view);
            overviewView = v.findViewById(R.id.overview_view);
            progressbar = v.findViewById(R.id.progressbar);
        }

        void bindView(NowPlaying nowPlaying) {

            Picasso.with(context)
                    .load(MyApplication.IMAGE_BASE_URL + nowPlaying.getBackdrop_path())
                    .into(posterView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if(progressbar != null)
                                progressbar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                        }
                    });

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
        holder.itemView.setOnClickListener(v -> onClickSubject.onNext(new Pair(position, holder.itemView)));
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

    public Observable<Pair<Integer, View>> getPositionClicks(){
        return onClickSubject;
    }

    public void update(List<NowPlaying> nowPlayingList) {
        this.nowPlayingList = nowPlayingList;
        notifyDataSetChanged();
    }
}
