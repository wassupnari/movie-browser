package com.movie.nari.movieapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import com.movie.nari.movieapp.R
import com.movie.nari.movieapp.databinding.ActivityMainBinding
import com.movie.nari.movieapp.viewmodel.MainViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initView()
//        viewModel.fetchData()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.let {
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }
        observeViewModel()
    }

    private fun initView() {
        val lm = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = lm

    }

    private fun observeViewModel() {
        viewModel.nowPlayingList.observe(this, Observer {
            Timber.d("received data")
            adapter = RecyclerViewAdapter(it)
            binding.recyclerview.adapter = adapter
//            adapter.update(it)
        })
    }
}
