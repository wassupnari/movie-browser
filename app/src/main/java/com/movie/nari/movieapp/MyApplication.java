package com.movie.nari.movieapp;

import android.app.Application;

import com.movie.nari.movieapp.model.DataRepository;
import com.movie.nari.movieapp.network.ApiService;
import com.movie.nari.movieapp.network.NetworkManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MyApplication extends Application {


    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
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
}
