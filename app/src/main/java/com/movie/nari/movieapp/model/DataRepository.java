package com.movie.nari.movieapp.model;

import com.movie.nari.movieapp.network.ApiService;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataRepository {

    private List<NowPlaying> nowPlayingList;
    private List<Trailers> trailersList;
    private ApiService apiService;

    public DataRepository(ApiService apiClient) {
        this.apiService = apiClient;
    }

    public Flowable<List<NowPlaying>> getNowPlayingList() {
        return apiService
                .getNowPlaying("a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .map(response -> {
                    return response.getResults();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
