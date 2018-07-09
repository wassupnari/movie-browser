package com.movie.nari.movieapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.movie.nari.movieapp.MyApplication
import com.movie.nari.movieapp.R
import com.movie.nari.movieapp.databinding.ActivityDetailBinding
import com.movie.nari.movieapp.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import android.support.constraint.ConstraintSet


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var isLandscape = false
    private var itemPosition : Int = 0

    companion object {
        private val INTENT_ITEM_POSITION : String = "item_position"
        private val INTENT_ORIENTATION : String = "device_orientation"

        fun newIntent(context: Context, position : Int, isLandscape : Boolean): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_ITEM_POSITION, position)
            intent.putExtra(INTENT_ORIENTATION, isLandscape)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemPosition = intent.getIntExtra(INTENT_ITEM_POSITION, 0)
        isLandscape = intent.getBooleanExtra(INTENT_ORIENTATION, false)
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
        updateConstraints()
    }

    private fun updateConstraints() {
        val set = ConstraintSet()
        val id = R.id.detail_poster_image
        val root_id = R.id.detail_content_layout
        if(isLandscape) { set.setDimensionRatio(id, "16:9") }
        else { set.setDimensionRatio(id, "2:3") }

        set.connect(id, ConstraintSet.TOP, root_id, ConstraintSet.TOP, 0)
        set.connect(id, ConstraintSet.LEFT, root_id, ConstraintSet.LEFT, 0)
        set.connect(id, ConstraintSet.RIGHT, root_id, ConstraintSet.RIGHT, 0)
        set.applyTo(binding.detailContentLayout)
    }

    private fun observeViewModel() {
        viewModel.nowPlayingList.observe(this, Observer {
            if(isLandscape) {
                Picasso.with(this)
                        .load(MyApplication.IMAGE_BASE_URL + it?.get(itemPosition)?.backdrop_path)
                        .into(binding.detailPosterImage)
            } else {
                Picasso.with(this)
                        .load(MyApplication.IMAGE_BASE_URL + it?.get(itemPosition)?.poster_path)
                        .into(binding.detailPosterImage)
            }

            binding.detailTitleView.text = it?.get(itemPosition)?.title
            binding.detailOverviewView.text = it?.get(itemPosition)?.overview
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        isLandscape = (newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE)
        updateConstraints()
        observeViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}