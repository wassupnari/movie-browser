package com.movie.nari.movieapp.model

import com.movie.nari.movieapp.MyApplication

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

typealias DataReceived = (List<NowPlaying>) -> Unit

class DataRepository {

    fun fetchNowPlayingList(dataReceived: DataReceived) {
         var playListObserver = object : Observer<List<NowPlaying>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(list: List<NowPlaying>) {
                dataReceived.invoke(list)
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }

        MyApplication.getInstance().client.getNowPlaying("a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .map { (results) -> results }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playListObserver)
    }
}
