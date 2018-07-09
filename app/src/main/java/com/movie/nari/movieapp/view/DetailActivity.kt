package com.movie.nari.movieapp.view

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.movie.nari.movieapp.MyApplication
import com.movie.nari.movieapp.R
import com.movie.nari.movieapp.databinding.ActivityDetailBinding
import com.movie.nari.movieapp.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter : RecyclerViewAdapter
    private var isLandscape = false
    private var itemPosition : Int = 0

    companion object {
        private val INTENT_ITEM_POSITION : String = "item_position"

        fun newIntent(context: Context, position : Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_ITEM_POSITION, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemPosition = intent.getIntExtra(INTENT_ITEM_POSITION, 0)
        initBinding()
        initView()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding.let {
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }

    }

    private fun initView() {
        ViewCompat.setTransitionName(binding.detailPosterImage, "poster")
    }

    private fun observeViewModel() {
        viewModel.nowPlayingList.observe(this, Observer {
            binding.detailTitleView.text = it!!.get(itemPosition).title
            Picasso.with(this)
                    .load(MyApplication.IMAGE_BASE_URL + it!!.get(itemPosition).poster_path)
                    .into(binding.detailPosterImage)
//            adapter.update(it)
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        isLandscape = (newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE)
        observeViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}