package com.movie.nari.movieapp;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.movie.nari.movieapp.network.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static final String BASE_URL = "https://api.themoviedb.org/3";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static MyApplication getInstance() {
        if(instance == null) {
            synchronized (MyApplication.class) {
                if(instance == null) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

    private OkHttpClient createHttpClient() {
        if(okHttpClient != null) return okHttpClient;

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        return clientBuilder.build();
    }

    public ApiService getClient() {
        if(retrofit != null) return retrofit.create(ApiService.class);

        retrofit = new Retrofit.Builder()
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }
}
