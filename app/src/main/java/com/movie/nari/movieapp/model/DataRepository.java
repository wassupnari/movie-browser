package com.movie.nari.movieapp.model;

import com.movie.nari.movieapp.MyApplication;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataRepository {

    private List<NowPlaying> nowPlayingList;
    private List<Trailers> trailersList;

    public DataRepository() {
        MyApplication.getInstance().getClient()
                .getNowPlaying("a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .map(response -> {
                    nowPlayingList = response.getResults();
                    return nowPlayingList;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
