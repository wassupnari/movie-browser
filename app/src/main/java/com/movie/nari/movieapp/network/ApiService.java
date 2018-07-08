package com.movie.nari.movieapp.network;

import com.movie.nari.movieapp.model.NowPlayingList;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/3/movie/now_playing")
    Flowable<NowPlayingList> getNowPlaying(@Query("api_key") String key);

}
