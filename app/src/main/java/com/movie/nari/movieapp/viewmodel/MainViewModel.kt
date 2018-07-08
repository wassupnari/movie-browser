package com.movie.nari.movieapp.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.provider.ContactsContract

import com.movie.nari.movieapp.MyApplication
import com.movie.nari.movieapp.model.DataRepository
import com.movie.nari.movieapp.model.NowPlaying
import com.movie.nari.movieapp.model.Trailers

class MainViewModel : ViewModel() {

    private val dataRepository = DataRepository()

    val nowPlayingList: MutableLiveData<List<NowPlaying>> = MutableLiveData()

    private val trailersList: List<Trailers>? = null

    init {
        fetchData()

    }

    fun fetchData() {
        dataRepository.fetchNowPlayingList{ nowPlayingList.postValue(it) }
    }
}
