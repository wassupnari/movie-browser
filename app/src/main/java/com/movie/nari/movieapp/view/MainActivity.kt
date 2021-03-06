package com.movie.nari.movieapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.movie.nari.movieapp.R
import com.movie.nari.movieapp.databinding.ActivityMainBinding
import com.movie.nari.movieapp.viewmodel.MainViewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : RecyclerViewAdapter
    private var isLandscape = false
    private var listState : Parcelable? = null
    private lateinit var layoutManager : LinearLayoutManager

    companion object {
        val LIST_STATE = "recyclerview_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
    }

    override fun onResume() {
        super.onResume()
        layoutManager.onRestoreInstanceState(listState)
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.let {
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }

    }

    private fun initView() {
        layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager

    }

    private fun observeViewModel() {
        viewModel.nowPlayingList.observe(this, Observer {
            binding.progressbar.visibility = View.GONE
            if(it?.size!! == 0) Timber.d("data null")
            adapter = RecyclerViewAdapter(it, isLandscape)
            binding.recyclerview.adapter = adapter
            adapter.positionClicks.subscribe({
                val options : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, it.second.findViewById(R.id.poster_image),"poster")
                startActivity(DetailActivity.newIntent(this, it.first, isLandscape), options.toBundle())
            })
//            adapter.update(it)
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        isLandscape = (newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE)
        observeViewModel()
        Timber.e("configuration changed, landscape ? " + isLandscape)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        listState = layoutManager.onSaveInstanceState()
        outState!!.putParcelable(LIST_STATE, listState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        listState = savedInstanceState?.getParcelable(LIST_STATE)
    }
}
