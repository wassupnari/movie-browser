package com.movie.nari.movieapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movie.nari.movieapp.R;
import com.movie.nari.movieapp.model.NowPlaying;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<NowPlaying> nowPlayingList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.test_view);
        }

        void bindView(NowPlaying nowPlaying) {
            mTextView.setText(nowPlaying.getTitle());
        }
    }

    public RecyclerViewAdapter(List<NowPlaying> list) {
        this.nowPlayingList = list;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_now_playing_itemview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(nowPlayingList.get(position));
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
