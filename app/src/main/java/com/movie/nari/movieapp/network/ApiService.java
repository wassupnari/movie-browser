package com.movie.nari.movieapp.network;

import com.movie.nari.movieapp.model.NowPlayingList;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/movie/now_playing")
    Flowable<NowPlayingList> getNowPlaying(@Field("api_key") String key);

}
