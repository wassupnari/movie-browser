package com.movie.nari.movieapp;

import android.app.Application;

import com.movie.nari.movieapp.model.DataRepository;
import com.movie.nari.movieapp.network.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MyApplication extends Application {

    private static final String BASE_URL = "https://api.themoviedb.org/";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

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

    private OkHttpClient createHttpClient() {
        if(okHttpClient != null) return okHttpClient;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        return okHttpClient;
    }

    public ApiService getClient() {
        if(retrofit != null) return retrofit.create(ApiService.class);

        retrofit = new Retrofit.Builder()
                .client(createHttpClient())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }
}
