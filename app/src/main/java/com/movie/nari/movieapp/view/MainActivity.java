package com.movie.nari.movieapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.movie.nari.movieapp.MyApplication;
import com.movie.nari.movieapp.viewModel.MainViewModel;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initView();
        fetchData();
    }

    private void initView() {

    }

    private void fetchData() {
        Disposable disposable = MyApplication.getInstance()
                .getDataRepository()
                .getNowPlayingList()
                .subscribe();
    }
}
